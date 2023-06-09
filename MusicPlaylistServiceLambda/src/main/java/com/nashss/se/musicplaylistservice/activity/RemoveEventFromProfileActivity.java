package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.RemoveEventFromProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.RemoveEventFromProfileResult;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.dynamodb.EventDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RemoveEventFromProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;
    private final EventDao eventDao;

    /**
     * Instantiates a new RemoveFromEventActivity object.
     * @param profileDao profileDao to access the album_track table.
     */
    @Inject
    public RemoveEventFromProfileActivity(ProfileDao profileDao, EventDao eventDao) {
        this.profileDao = profileDao;
        this.eventDao = eventDao;
    }


    public RemoveEventFromProfileResult handleRequest(final RemoveEventFromProfileRequest removeFromEventRequest) {
        log.info("Received RemoveFromEventRequest {} ", removeFromEventRequest);

        String id = removeFromEventRequest.getProfileId();
        String profileIdToRemove = removeFromEventRequest.getEventId();
        Event eventToRemove = eventDao.getEvent(profileIdToRemove);
        if(eventToRemove.getEventCreator().equals(id)){
            Set<String> remainderEvents = eventDao.deleteEvent(profileIdToRemove, id);
            return RemoveEventFromProfileResult.builder()
                .withEventList(new ArrayList<String>(remainderEvents))
                .build();
        } else {
            profileDao.getProfile(removeFromEventRequest.getProfileId());
            System.out.println(id + " " + profileIdToRemove);
            Set<String> updatedList = profileDao.removeProfileFromEvent(id, profileIdToRemove);
            List<String> list = new ArrayList<>(updatedList);
            return RemoveEventFromProfileResult.builder()
                .withEventList(list)
                .build();
        }
        


    }
}
