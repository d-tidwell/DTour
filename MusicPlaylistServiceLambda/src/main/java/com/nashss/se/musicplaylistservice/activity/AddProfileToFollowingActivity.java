package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.AddProfileToFollowingRequest;
import com.nashss.se.musicplaylistservice.activity.results.AddProfileToFollowingResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;


/**
 * Implementation of the AddSongToPlaylistActivity for the MusicPlaylistService's AddSongToPlaylist API.
 * This API allows the customer to add a song to their existing playlist.
 */

public class AddProfileToFollowingActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;


    /**
     * Instantiates a new AddSongToPlaylistActivity object.
     * @param profileDao AlbumTrackDao to access the album_track table.
     */
    @Inject
    public AddProfileToFollowingActivity(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    /**
     * This method handles the incoming request by adding a profile to following list
     * It then returns the updated following list
     * If the profile does not exist, this should throw a ProfileNotFoundException.
     * @param addProfileToFollowingRequest request object containing id
     *                                 to retrieve the data
     * @return AddProfileToFollowingResult result object containing the playlist's updated list of
     *                                 API defined {@link ProfileModel}s
     */
    public AddProfileToFollowingResult handleRequest(final AddProfileToFollowingRequest addProfileToFollowingRequest) {
        log.info("Received AddProfileToFollowingRequest {} ", addProfileToFollowingRequest);

        String id = addProfileToFollowingRequest.getId();
        String idToAdd = addProfileToFollowingRequest.getIdToAdd();

        profileDao.getProfile(id);

        profileDao.getProfile(idToAdd);

        List<String> updatedListProfiles = profileDao.addProfileToFollowersList(id, idToAdd);

        List <ProfileModel> profileModel = new ModelConverter().toProfileModelList(updatedListProfiles);

        return AddProfileToFollowingResult.builder()
                .withProfileModelListList(profileModel)
                .build();
    }
}

