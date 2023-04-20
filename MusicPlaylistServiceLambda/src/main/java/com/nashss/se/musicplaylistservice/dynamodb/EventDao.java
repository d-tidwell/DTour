package com.nashss.se.musicplaylistservice.dynamodb;

import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.exceptions.EventNotFoundException;
import com.nashss.se.musicplaylistservice.exceptions.EventTimeIsInvalidException;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for an event using {@link Event} to represent the model in DynamoDB.
 */
@Singleton
public class EventDao {
    private final Logger log = LogManager.getLogger();
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
            if(!category.isEmpty()){
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
