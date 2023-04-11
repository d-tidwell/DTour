package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.AddEventToProfileRequest;
import com.nashss.se.musicplaylistservice.activity.requests.AddSongToPlaylistRequest;
import com.nashss.se.musicplaylistservice.activity.results.AddEventToProfileResult;
import com.nashss.se.musicplaylistservice.activity.results.AddSongToPlaylistResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.AlbumTrackDao;
import com.nashss.se.musicplaylistservice.dynamodb.EventDao;
import com.nashss.se.musicplaylistservice.dynamodb.PlaylistDao;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.AlbumTrack;
import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.dynamodb.models.Playlist;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.models.EventModel;
import com.nashss.se.musicplaylistservice.models.SongModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

/**
 * Implementation of the AddEventToProfileActivity for the AddEventToProfile API.
 *
 * This API allows the user to add an event to their existing event calendar.
 */
public class AddEventToProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;
    private final EventDao eventDao;

    /**
     * Instantiates a new AddSongToPlaylistActivity object.
     *
     * @param profileDao ProfileDao to access the profile.
     * @param eventDao eventDao to access the events table.
     */
    @Inject
    public AddEventToProfileActivity(ProfileDao profileDao, EventDao eventDao) {
        this.profileDao = profileDao;
        this.eventDao = eventDao;
    }

    /**
     * This method handles the incoming request by adding an additional event
     * to a profile and persisting the updated profile.
     * <p>
     * It then returns the updated event list of the profile.
     * <p>
     * If the profile does not exist, this should throw a ProfileNotFoundException.
     * <p>
     * If the event does not exist, this should throw an EventNotFoundException.
     *
     * @param addEventToProfileRequest request object containing the profile and eventId
     *                                 to retrieve the event data
     * @return addEventToProfileResult result object containing the profile's updated list of
     *                                 API defined {@link EventModel}s
     */
    public AddEventToProfileResult handleRequest(final AddEventToProfileRequest addEventToProfileRequest) {
        log.info("Received AddEventToProfileRequest {} ", addEventToProfileRequest);

        String eventId = addEventToProfileRequest.getEventId();

        String profile = addEventToProfileRequest.getProfileId();

        Set<String> events = eventDao.addEventToProfile(eventId, profile);

        List<String> eventModels = new ModelConverter().toEventModelList(events);
        return AddEventToProfileResult.builder()
                .withEventList(eventModels)
                .build();
    }
}
