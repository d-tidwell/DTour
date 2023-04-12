package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.GetProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetProfileResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.models.ProfileModel;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.inject.Inject;

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

        ProfileModel profileModel = new ModelConverter().toProfileModel(profile);

        return GetProfileResult.builder()
                .withProfileModel(profileModel)
                .build();
    }

}
