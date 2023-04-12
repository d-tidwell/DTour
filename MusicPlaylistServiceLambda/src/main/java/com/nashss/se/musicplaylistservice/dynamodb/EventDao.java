package com.nashss.se.musicplaylistservice.dynamodb;

import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.exceptions.EventNotFoundException;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for an event using {@link Event} to represent the model in DynamoDB.
 */
@Singleton
public class EventDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates an EventDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public EventDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Returns the {@link Event} corresponding to the specified eventId.
     *
     * @param eventId the Event ID
     * @return the stored Event, or null if none was found.
     */
    public Event getEvent(String eventId) {
        Event event = this.dynamoDbMapper.load(Event.class, eventId);

        if (event == null) {
            metricsPublisher.addCount(MetricsConstants.GETEVENT_EVENTNOTFOUND_COUNT, 1);
            throw new EventNotFoundException("Could not find event with id " + eventId);
        }
        metricsPublisher.addCount(MetricsConstants.GETEVENT_EVENTNOTFOUND_COUNT, 0);
        return event;
    }

    /**
     * Saves (creates or updates) the given event.
     *
     * @param event The event to save
     * @return The Event object that was saved
     */
    //create vs save: choose one but not both save should probably be the name since that is what we are doing on the
    // backend so since this is just checking the datetime issue lets call this that and the other save

    public boolean checkEventDateTime(String eventTime) {
        ZonedDateTime now = ZonedDateTime.now();
        //just make sure this gets tested between now and Friday so we are sure 100% its effective
        if(ZonedDateTime.parse(eventTime).isAfter(now)){
            return true;
        } else {
            //create this exception handling I just put this here as an example
            throw new EventTimeIsInvalidException("Events must be for future dates");
            return false;
        }
    }

    /**
     * Adds an event to the user's profile.
     *
     * @param event The event to add
     * @Return the updated event list
     */

    //move all of this logic to the profileDao since it already has the stuff you need to make it work
//    public Set<String> addEventToProfile(String event, String profileId) {
/////        ProfileDao profileDao = new ProfileDao(dynamoDbMapper, metricsPublisher);
//        getEvent(event);
//        Profile profile = profileDao.getProfile(profileId);
//        Set<String> events = profile.getEvents();
//        events.add(event);
//        this.dynamoDbMapper.save(profile);
//
//        return events;

//    }

    /**
     * Creates a new Event object.
     *
     */
    //let this also do double duty as an update method so we don't have to make two seperate things to accomplish the same
    //backend functionality
    public Event saveEvent(boolean isNew,String eventId, String name, String eventCreator, String address, String description,
                             String dateTime, Set<String> category) {
        Event event = new Event();

        //if this is new and this event is after the date time of now
        if(isNew && checkEventDateTime(dateTime)){
            event.setEventId();
            event.setName(name);
            event.setEventCreator(eventCreator);
            event.setAddress(address);
            event.setDescription(description);
            event.setDateTime(dateTime);
            event.setCategory(new HashSet<>());

        //if its not a new event this must an update
        } else {
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
                if(checkEventDateTime(dateTime)) {
                    event.setDateTime(dateTime);
                }
            }
            if(!category.isEmpty())){
                Event oldEvent = this.getEvent(eventId);
                Set<String> categories = oldEvent.getCategory();
                categories.addAll(category);
                event.setCategory(categories);
            }
        }

        this.dynamoDbMapper.save(event);

        return event;
    }

    /**
     * Perform a search (via a "scan") of the events table for events matching the given criteria.
     *
     * Both "name" and "category" attributes are searched.
     * The criteria are an array of Strings. Each element of the array is search individually.
     * ALL elements of the criteria array must appear in the eventName or the categories (or both).
     * Searches are CASE SENSITIVE.
     *
     * @param criteria an array of String containing search criteria.
     * @return a List of Event objects that match the search criteria.
     */
    public List<Event> searchEvents(String[] criteria) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

        if (criteria.length > 0) {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            String valueMapNamePrefix = ":c";

            StringBuilder nameFilterExpression = new StringBuilder();
            StringBuilder categoryFilterExpression = new StringBuilder();

            for (int i = 0; i < criteria.length; i++) {
                valueMap.put(valueMapNamePrefix + i,
                        new AttributeValue().withS(criteria[i]));
                nameFilterExpression.append(
                        filterExpressionPart("name", valueMapNamePrefix, i));
                categoryFilterExpression.append(
                        filterExpressionPart("category", valueMapNamePrefix, i));
            }

            dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
            dynamoDBScanExpression.setFilterExpression(
                    "(" + nameFilterExpression + ") or (" + categoryFilterExpression + ")");
        }

        return this.dynamoDbMapper.scan(Event.class, dynamoDBScanExpression);
    }

    private StringBuilder filterExpressionPart(String target, String valueMapNamePrefix, int position) {
        String possiblyAnd = position == 0 ? "" : "and ";
        return new StringBuilder()
                .append(possiblyAnd)
                .append("contains(")
                .append(target)
                .append(", ")
                .append(valueMapNamePrefix).append(position)
                .append(") ");
    }
}
