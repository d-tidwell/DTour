package com.nashss.se.musicplaylistservice.activity;

//???? clean up all your imports before you commit anything please
import com.nashss.se.musicplaylistservice.activity.requests.CreateEventRequest;
import com.nashss.se.musicplaylistservice.activity.requests.CreatePlaylistRequest;
import com.nashss.se.musicplaylistservice.activity.results.CreateEventResult;
import com.nashss.se.musicplaylistservice.activity.results.CreatePlaylistResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.EventDao;
import com.nashss.se.musicplaylistservice.dynamodb.PlaylistDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.dynamodb.models.Playlist;
import com.nashss.se.musicplaylistservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.musicplaylistservice.models.EventModel;
import com.nashss.se.musicplaylistservice.models.PlaylistModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;

/**
 * Implementation of the CreateEventActivity for the MusicPlaylistService's CreateEvent API.
 * <p>
 * This API allows the customer to create a new event.
 */
public class CreateEventActivity {
    private final Logger log = LogManager.getLogger();
    private final EventDao eventDao;

    /**
     * Instantiates a new CreateEventActivity object.
     *
     * @param eventDao EventDao to access the events table.
     */
    @Inject
    public CreateEventActivity(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    /**
     * This method handles the incoming request by persisting a new event
     * with the provided event name and user from the request.
     * <p>
     * It then returns the newly created event.
     * <p>
     * If the provided event name or user has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createEventRequest request object containing the event name and customer ID
     *                              associated with it
     * @return createEventResult result object containing the API defined {@link EventModel}
     */
    public CreateEventResult handleRequest(final CreateEventRequest createEventRequest) {
        log.info("Received CreateEventRequest {}", createEventRequest);

        //added true here so we know we are creating not updating see the eventDao for disambiguation
        //added null bc we don't have an id to give bc this is a new event
        Event newEvent = eventDao.saveEvent(true, null, createEventRequest.getName(), createEventRequest.getEventCreator(),
                createEventRequest.getAddress(), createEventRequest.getDescription(),
                createEventRequest.getDateTime(), createEventRequest.getCategory());

        EventModel eventModel = new ModelConverter().toEventModel(newEvent);
        return CreateEventResult.builder()
                .withEvent(eventModel)
                .build();
    }
}
