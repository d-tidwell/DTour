package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.AddFollowingToProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.AddFollowingToProfileResult;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Implementation of the AddSongToPlaylistActivity for the MusicPlaylistService's AddSongToPlaylist API.
 * This API allows the customer to add a song to their existing playlist.
 */

public class AddFollowingToProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;


    /**
     * Instantiates a new AddSongToPlaylistActivity object.
     * @param profileDao AlbumTrackDao to access the album_track table.
     */
    @Inject
    public AddFollowingToProfileActivity(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    /**
     * This method handles the incoming request by adding a profile to following list
     * It then returns the updated following list
     * If the profile does not exist, this should throw a ProfileNotFoundException.
     * @param addFollowingToProfileRequest request object containing id
     *                                 to retrieve the data
     * @return AddProfileToFollowingResult result object containing the playlist's updated list of
     *                                 API defined {@link ProfileModel}s
     */
    public AddFollowingToProfileResult handleRequest(final AddFollowingToProfileRequest addFollowingToProfileRequest) {
        log.info("Received AddFollowingToProfileRequest {} ", addFollowingToProfileRequest);

        String id = addFollowingToProfileRequest.getId();
        String idToAdd = addFollowingToProfileRequest.getIdToAdd();

        profileDao.getProfile(id);

        profileDao.getProfile(idToAdd);

        Set<String> updatedListProfiles = profileDao.addProfileToFollowersList(id, idToAdd);

        return AddFollowingToProfileResult.builder()
                .withProfileList(new ArrayList<String>(updatedListProfiles))
                .build();
    }
}

