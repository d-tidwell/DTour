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

        // Using MusicPlaylistServiceUtils to verify no illegal chars.
        if(!MusicPlaylistServiceUtils.isValidString(createProfileRequest.getFirstName())){
            throw new InvalidAttributeValueException("Your Name cannot contain illegal characters");
        }
        Profile newProfile = new Profile();
        newProfile.setId(createProfileRequest.getEmailAddress());
        newProfile.setFirstName(createProfileRequest.getFirstName());
        newProfile.setLastName(createProfileRequest.getLastName());
        newProfile.setLocation(createProfileRequest.getLocation());
        newProfile.setGender(createProfileRequest.getGender());
        newProfile.setDateOfBirth(ZonedDateTime.parse(createProfileRequest.getDateOfBirth()));
        newProfile.setEvents(createProfileRequest.getEventList());
        newProfile.setFollowing(createProfileRequest.getFollowerList());

        profileDao.saveProfile(newProfile.getId(), newProfile.getFirstName(), newProfile.getLastName(), newProfile.getLocation(), newProfile.getGender(), newProfile.getDateOfBirth(),newProfile.getEvents(),newProfile.getFollowing());

        ProfileModel profileModel = new ModelConverter().toProfileModel(newProfile);
        return CreateProfileResult.builder()
                .withProfile(profileModel)
                .build();
    }

}
