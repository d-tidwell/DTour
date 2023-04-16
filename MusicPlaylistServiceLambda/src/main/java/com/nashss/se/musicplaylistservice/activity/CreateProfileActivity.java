package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.CreateProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.CreateProfileResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

import com.nashss.se.projectresources.music.playlist.servic.util.MusicPlaylistServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.ZonedDateTime;

public class CreateProfileActivity {

    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;
    /**
     * Instantiates a new CreateProfileActivity object.
     *
     * @param profileDao ProfileDao to access the profile table.
     */
    @Inject
    public CreateProfileActivity(ProfileDao profileDao){
        this.profileDao = profileDao;
    }
    public CreateProfileResult handleRequest(final CreateProfileRequest createProfileRequest){
        log.info("Received CreateProfileRequest{}", createProfileRequest);

        //
        if(!MusicPlaylistServiceUtils.isValidString(createProfileRequest.getFirstName())){
            throw new InvalidAttributeValueException("Your Name cannot contain illegal characters");
        }

        Profile newProfile = profileDao.saveProfile(true,
                createProfileRequest.getId(), createProfileRequest.getFirstName(),
                createProfileRequest.getLastName(), createProfileRequest.getLocation(),
                createProfileRequest.getGender(), ZonedDateTime.parse(createProfileRequest.getDateOfBirth()));

        ProfileModel profileModel = new ModelConverter().toProfileModel(newProfile);
        return CreateProfileResult.builder()
                .withProfile(profileModel)
                .build();
    }

}
