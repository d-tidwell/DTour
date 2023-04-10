package com.nashss.se.musicplaylistservice.dynamodb;

import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.dynamodb.models.Playlist;
import com.nashss.se.musicplaylistservice.exceptions.PlaylistNotFoundException;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Instantiates a PlaylistDao object.
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
            metricsPublisher.addCount(MetricsConstants.GETEVENT_PLAYLISTNOTFOUND_COUNT, 1);
            throw new PlaylistNotFoundException("Could not find event with id " + eventId);
        }
        metricsPublisher.addCount(MetricsConstants.GETEVENT_PLAYLISTNOTFOUND_COUNT, 0);
        return event;
    }

    /**
     * Saves (creates or updates) the given event.
     *
     * @param event The event to save
     * @return The Event object that was saved
     */
    public Event saveEvent(Event event) {
        LocalDateTime now = LocalDateTime.now();
        if (event.getDateTime().toLocalDateTime().isAfter(now)) {
            this.dynamoDbMapper.save(event);
        }
        return event;
    }

    /**
     * Perform a search (via a "scan") of the events table for events matching the given criteria.
     *
     * Both "name" and "category" attributes are searched.
     * The criteria are an arrayt of Strings. Each element of the array is search individually.
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
