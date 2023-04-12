package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.RemoveFromFollowingRequest;
import com.nashss.se.musicplaylistservice.activity.results.RemoveFromFollowingResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class RemoveFromFollowingActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;


    /**
     * Instantiates a new RemoveFromFollowingActivity object.
     * @param profileDao AlbumTrackDao to access the album_track table.
     */
    @Inject
    public RemoveFromFollowingActivity(ProfileDao profileDao) {
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
    public RemoveFromFollowingResult handleRequest(final RemoveFromFollowingRequest removeFromFollowingRequest) {
        log.info("Received RemoveFromFollowingRequest {} ", removeFromFollowingRequest);

        String id = removeFromFollowingRequest.getId();
        String profileIdToRemove = removeFromFollowingRequest.getProfileIdToRemove();

        profileDao.getProfile(id);

        profileDao.getProfile(profileIdToRemove);

        List<String> updatedListProfiles =  profileDao.removeProfileFromFollowing(id, profileIdToRemove);
        // you are doing the same thing here as you did in the add event see that for why it doesnt make sense
        List<ProfileModel> profileModel = new ModelConverter().toProfileModelList(updatedListProfiles);

        return RemoveFromFollowingResult.builder()
                .withProfileModelList(profileModel)
                .build();
    }
}



