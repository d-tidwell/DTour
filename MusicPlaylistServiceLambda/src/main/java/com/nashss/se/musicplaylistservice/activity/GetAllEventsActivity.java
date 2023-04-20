package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.GetAllEventsRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetAllEventsResult;
import com.nashss.se.musicplaylistservice.dynamodb.EventDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetAllEventsActivity {
    private final Logger log = LogManager.getLogger();
    private final EventDao eventDao;

    @Inject
    public GetAllEventsActivity(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public GetAllEventsResult handleRequest(){
        log.info("Receive GetAllEventsRequest {} ", "called Get All Events");

        List<Event> listEvents = eventDao.getAllEvents();

        return GetAllEventsResult.builder()
                .withEventList(listEvents)
                .build();
    }
}