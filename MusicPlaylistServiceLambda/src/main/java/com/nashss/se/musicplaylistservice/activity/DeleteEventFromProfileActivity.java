package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.DeleteEventFromProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.RemoveEventFromProfileResult;
import com.nashss.se.musicplaylistservice.dynamodb.EventDao;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.exceptions.InvalidAttributeException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

public class DeleteEventFromProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;
    private final EventDao eventDao;

    /**
     * Instantiates a new RemoveFromEventActivity object.
     * @param profileDao profileDao to access the album_track table.
     */
    @Inject
    public DeleteEventFromProfileActivity(ProfileDao profileDao, EventDao eventDao) {
        this.profileDao = profileDao;
        this.eventDao = eventDao;
    }


    public RemoveEventFromProfileResult handleRequest(final DeleteEventFromProfileRequest deleteFromEventRequest) {
        log.info("Received RemoveFromEventRequest {} ", deleteFromEventRequest);

        String id = deleteFromEventRequest.getProfileId();
        String eventId = deleteFromEventRequest.getEventId();
        
        Profile authProf = this.profileDao.getProfile(id);
        Event eventGone = this.eventDao.getEvent(eventId);
        if(authProf.getId().equals(eventGone.getEventCreator())){
            Set<String> returnedEvents = eventDao.deleteEvent(eventId, id);
            return RemoveEventFromProfileResult.builder()
            .withEventList(new ArrayList<String>(returnedEvents))
            .build();
        } else {
            throw new InvalidAttributeException("You must own the event to delete it");
        }
    }
}
