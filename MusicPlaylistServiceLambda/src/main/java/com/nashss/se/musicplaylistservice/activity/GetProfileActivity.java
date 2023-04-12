package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.GetProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetProfileResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.exceptions.ProfileNotFoundException;
import com.nashss.se.musicplaylistservice.models.ProfileModel;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class GetProfileActivity {

    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;

    @Inject
    public GetProfileActivity(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public GetProfileResult handleRequest(final GetProfileRequest getProfileRequest){
        log.info("Receive GetProfileResult {} ", getProfileRequest);

        String id = getProfileRequest.getProfileId();
        Profile profile = profileDao.getProfile(id);
        if (profile == null) {
            throw new ProfileNotFoundException("Profile does not exist, please try again with another id.");
        }

        Profile newProfile = new Profile();
        newProfile.setId(getProfileRequest.getProfileId());
        newProfile.setFirstName(getProfileRequest.getFirstName());
        newProfile.setLastName(getProfileRequest.getLastName());
        newProfile.setLocation(getProfileRequest.getLocation());
        newProfile.setGender(getProfileRequest.getGender());
        newProfile.setDateOfBirth(getProfileRequest.getDateOfBirth());
        newProfile.setFollowing(getProfileRequest.getFollowing());
        newProfile.setEvents(getProfileRequest.getEvents());

        profileDao.saveProfile(newProfile.getId(), newProfile.getFirstName(), newProfile.getLastName(),
                newProfile.getLocation(), newProfile.getGender(), newProfile.getDateOfBirth(), newProfile.getFollowing(), newProfile.getEvents());

        ProfileModel profileModel = new ModelConverter().toProfileModel(newProfile);

        return GetProfileResult.builder()
                .withProfileModel(profileModel)
                .build();
    }

}
