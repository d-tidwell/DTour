package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.RemoveFollowingFromProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.RemoveFollowingFromProfileResult;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RemoveFollowingFromProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;


    /**
     * Instantiates a new RemoveFromFollowingActivity object.
     * @param profileDao AlbumTrackDao to access the album_track table.
     */
    @Inject
    public RemoveFollowingFromProfileActivity(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    /**
     * This method handles the incoming request by adding a profile to following list
     * It then returns the updated following list
     * If the profile does not exist, this should throw a ProfileNotFoundException.
     * @param removeFromFollowingRequest request object containing id
     *                                     to retrieve the data
     * @return AddProfileToFollowingResult result object containing the playlist's updated list of
     * API defined {@link ProfileModel}s
     */
    public RemoveFollowingFromProfileResult handleRequest(final RemoveFollowingFromProfileRequest removeFromFollowingRequest) {
        log.info("Received RemoveFromFollowingRequest {} ", removeFromFollowingRequest);

        String id = removeFromFollowingRequest.getId();
        String profileIdToRemove = removeFromFollowingRequest.getProfileIdToRemove();

        profileDao.getProfile(removeFromFollowingRequest.getId());

        Set<String> updatedList = profileDao.removeProfileFromFollowing(id, profileIdToRemove);
        List<String> list = new ArrayList<>(updatedList);
        return RemoveFollowingFromProfileResult.builder()
                .withProfileList(list)
                .build();


    }
}



