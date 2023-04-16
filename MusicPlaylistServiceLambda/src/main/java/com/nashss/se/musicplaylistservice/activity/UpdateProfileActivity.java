package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.UpdateProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.UpdateProfileResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;
import com.nashss.se.projectresources.music.playlist.servic.util.MusicPlaylistServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import java.time.ZonedDateTime;

public class UpdateProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;
    private final MetricsPublisher metricsPublisher;

    @Inject
    public UpdateProfileActivity(ProfileDao profileDao, MetricsPublisher metricsPublisher){
        this.profileDao = profileDao;
        this.metricsPublisher = metricsPublisher;
    }

    public UpdateProfileResult handleRequest(final UpdateProfileRequest updateProfileRequest)  {
        log.info("Received UpdateProfileRequest{}", updateProfileRequest);

        //checks if the profile is valid or throws an error
        Profile profile = profileDao.getProfile(updateProfileRequest.getProfileId());

        //don't let them change there id number or havoc ensues in the database
        Profile newProfile = profileDao.saveProfile(false, profile.getId(),
               updateProfileRequest.getFirstName(), updateProfileRequest.getLastName(), updateProfileRequest.getLocation(),
               updateProfileRequest.getGender(), ZonedDateTime.parse(updateProfileRequest.getDateOfBirth()));


        //this publishedExceptionMetrics isn't really helpful information the way that it is implemented because there is no
        // other case in which it is executed. if you want to use something like this check both cases
        //otherwise you are always getting the same result all the time. What if the value is invalid and what attributeChange
        // are you not allowing to change??

        publishExceptionMetrics(false,false);
        return UpdateProfileResult.builder()
                .withProfile(new ModelConverter().toProfileModel(newProfile))
                .build();
    }

    //see above - this needs work to be a valid method to track any useful metric
    private void publishExceptionMetrics(final boolean isInvalidAttributeValue,
                                         final boolean isInvalidAttributeChange) {
        metricsPublisher.addCount(MetricsConstants.UPDATEPROFILE_INVALIDATTRIBUTEVALUE_COUNT,
                isInvalidAttributeValue ? 1 : 0);
        metricsPublisher.addCount(MetricsConstants.UPDATEPROFILE_INVALIDATTRIBUTECHANGE_COUNT,
                isInvalidAttributeChange ? 1 : 0);
    }
}
