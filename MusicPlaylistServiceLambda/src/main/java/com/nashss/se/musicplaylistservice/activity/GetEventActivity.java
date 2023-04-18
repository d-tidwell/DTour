package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.GetEventRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetEventResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.EventDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.models.EventModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetEventActivity {
    private final Logger log = LogManager.getLogger();
    private final EventDao eventDao;

    @Inject
    public GetEventActivity(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public GetEventResult handleRequest(final GetEventRequest getEventRequest){

        log.info("Receive GetEventResult {} ", getEventRequest);


        String id = getEventRequest.getEventId();
        Event event = eventDao.getEvent(id);

        System.out.println("id: " + id);
        System.out.println("event " + event);

        EventModel eventModel = new ModelConverter().toEventModel(event);

        return GetEventResult.builder()
                .withEventModel(eventModel)
                .build();
    }
}

