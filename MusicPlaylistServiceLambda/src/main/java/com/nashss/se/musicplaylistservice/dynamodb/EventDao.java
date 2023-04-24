package com.nashss.se.musicplaylistservice.dynamodb;

import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.exceptions.EventNotFoundException;
import com.nashss.se.musicplaylistservice.exceptions.EventTimeIsInvalidException;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import java.time.ZonedDateTime;
import java.util.*;


import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for an event using {@link Event} to represent the model in DynamoDB.
 */
@Singleton
public class EventDao {

    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    private final ProfileDao profileDao;

    /**
     * Instantiates an EventDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public EventDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher, ProfileDao profileDao) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
        this.profileDao = profileDao;
    }

     /**
     * Get a chronologically sorted list of {@link Event} by most recent
     * @return Sorted List of Events
     */
    public List<Event> getAllEvents() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Event> unsortedEventList = dynamoDbMapper.scan(Event.class, scanExpression);
        List<Event> sortedEventList = new ArrayList<>(unsortedEventList);
        Collections.sort(sortedEventList, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                ZonedDateTime zonedDateTime1 = ZonedDateTime.parse(e1.getDateTime());
                ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(e2.getDateTime());
                return zonedDateTime1.compareTo(zonedDateTime2);
            }
        });
        return sortedEventList;
    }

    /**
     * Saves (creates or updates) the given event.
     *
     * @param eventTime The event to save
     * @return The Event object that was saved
     */

    public boolean checkEventDateTime(String eventTime) {
        ZonedDateTime eventDate = ZonedDateTime.parse(eventTime);
        ZonedDateTime now = ZonedDateTime.now(eventDate.getZone());

        if(eventDate.isAfter(now)){
            return true;
        } else {

            throw new EventTimeIsInvalidException("Events must be for future dates");
        }
    }


    /**
     * Creates a new Event object.
     *
     */
    public Event saveEvent(boolean isNew, String eventId, String name, String eventCreator, String address, String description,
                             String dateTime, Set<String> category) {
        Event event = new Event();
        Profile profileEvent = profileDao.getProfile(eventCreator);
        Set<String> eventsAttending = profileEvent.getEvents();
        if(isNew && checkEventDateTime(dateTime)){
            event.setEventId(event.generateId());
            event.setName(name);
            event.setEventCreator(eventCreator);
            event.setAddress(address);
            event.setDescription(description);
            event.setDateTime(dateTime);

            event.setCategory(new HashSet<>(category));
            event.setAttendees(new HashSet<>(Collections.singleton(eventCreator)));

        } else {
            Event oldEvent = this.getEvent(eventId);
            event.setEventId(eventId);
            if(name != null && !name.isEmpty()){
                event.setName(name);
            }
            if(eventCreator != null && !eventCreator.isEmpty()){
                event.setEventCreator(eventCreator);
            }
            if(address != null && !address.isEmpty()){
                event.setAddress(address);
            }
            if(description != null && !description.isEmpty()){
                event.setDescription(description);
            }
            if(dateTime != null && !dateTime.isEmpty()){
                event.setDateTime(dateTime);
            }
            
            if(!category.isEmpty()){
                Set<String> categories = oldEvent.getCategory();
                categories.addAll(category);
                event.setCategory(categories);
            }
            event.setAttendees(oldEvent.getAttendees());
        }

        this.dynamoDbMapper.save(event);
        //add the new event to the profiles list of attending events
        eventsAttending.add(eventId);
        this.dynamoDbMapper.save(profileEvent);

        return event;
    }
    /**
     * Iterates over all the attendees and removes the {@link Event} from their list.
     * Deletes the event from the database.
     * @param eventId the id of the event to delete
     * @param profileId the id of the {@link Profile} of the {@link Event} owner
     * @return updated list of events the profile is attending
     */

    public Set<String> deleteEvent(String eventId, String profileId) {
        Event event = this.getEvent(eventId);
        Set<String> attending = event.getAttendees();
        Set<String> returnSet = new HashSet<>();
        for(String email: attending){
            Profile working = profileDao.getProfile(email);
            Set<String> listed = working.getEvents();
            listed.remove(eventId);
            if(email.equals(profileId)){
                returnSet.addAll(listed);
            }
            working.setEvents(listed);
            this.dynamoDbMapper.save(working);
        }
        this.dynamoDbMapper.delete(event);
        return returnSet;

    }

    /**
     * Returns the {@link Event} corresponding to the specified eventId.
     *
     * @param id the Event ID
     * @return the stored Event, or null if none was found.
     */

    public Event getEvent(String id) {

        Event event = this.dynamoDbMapper.load(Event.class, id);

        if (Objects.isNull(event)) {
            metricsPublisher.addCount(MetricsConstants.GETEVENT_EVENTNOTFOUND_COUNT, 1);
            throw new EventNotFoundException("Could not find event with id " + id);
        }

        metricsPublisher.addCount(MetricsConstants.GETEVENT_EVENTNOTFOUND_COUNT, 0);
        return event;
    }

}
