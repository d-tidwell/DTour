package com.nashss.se.musicplaylistservice.activity;
import com.nashss.se.musicplaylistservice.activity.requests.UpdateEventRequest;
import com.nashss.se.musicplaylistservice.activity.results.UpdateEventResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.EventDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;
import com.nashss.se.musicplaylistservice.models.EventModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the UpdateEventActivity for the MusicPlaylistService's UpdateEvent API.
 *
 * This API allows the customer to update their saved event's information.
 */
public class UpdateEventActivity {
    private final Logger log = LogManager.getLogger();
    private final EventDao eventDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new UpdateEventActivity object.
     *
     * @param eventDao to access the events table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public UpdateEventActivity(EventDao eventDao, MetricsPublisher metricsPublisher) {
        this.eventDao = eventDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the event, updating it,
     * and persisting the event.
     * <p>
     * It then returns the updated event.
     * <p>
     * If the event does not exist, this should throw an EventNotFoundException.
     * <p>
     * If the provided event name or eventId has invalid characters, throws an
     * InvalidAttributeValueException
     * <p>
     * If the request tries to update the eventId,
     * this should throw an InvalidAttributeChangeException
     *
     * @param updateEventRequest request object containing the event eventId, event name, eventCreator, address, description, dateTime,
     *                           category, and attendees.
     *                              associated with it
     * @return updateEventResult result object containing the API defined {@link EventModel}
     */
    public UpdateEventResult handleRequest(final UpdateEventRequest updateEventRequest) {
        log.info("Received UpdateEventRequest {}", updateEventRequest);

        Event event = eventDao.getEvent(updateEventRequest.getEventId());

        if (!event.getCreatedBy().equals(updateEventRequest.getProfileId())) {
            publishExceptionMetrics(false, true);
            throw new SecurityException("You must own an event to update it.");
        }

        Event updateEvent = eventDao.saveEvent(false, updateEventRequest.getEventId(), updateEventRequest.getName(),
                                  updateEventRequest.getEventCreator(), updateEventRequest.getAddress(),
                                  updateEventRequest.getDescription(), updateEventRequest.getDateTime(),
                                  updateEventRequest.getCategory());

        publishExceptionMetrics(false, false);

        return UpdateEventResult.builder()
                .withEvent(new ModelConverter().toEventModel(updateEvent))
                .build();
    }

    /**
     * Helper method to publish exception metrics.
     * @param isInvalidAttributeValue indicates whether InvalidAttributeValueException is thrown
     * @param isInvalidAttributeChange indicates whether InvalidAttributeChangeException is thrown
     */
    private void publishExceptionMetrics(final boolean isInvalidAttributeValue,
                                         final boolean isInvalidAttributeChange) {

        metricsPublisher.addCount(MetricsConstants.UPDATEEVENT_INVALIDATTRIBUTEVALUE_COUNT,
                isInvalidAttributeValue ? 1 : 0);
        metricsPublisher.addCount(MetricsConstants.UPDATEEVENT_INVALIDATTRIBUTECHANGE_COUNT,
                isInvalidAttributeChange ? 1 : 0);
    }
}

