package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.UpdateProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.UpdateProfileResult;
import com.nashss.se.musicplaylistservice.dynamodb.ProfileDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;
import com.nashss.se.projectresources.music.playlist.servic.util.MusicPlaylistServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;

public class UpdateProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;
    private final MetricsPublisher metricsPublisher;

    @Inject
    public UpdateProfileActivity(ProfileDao profileDao, MetricsPublisher metricsPublisher){
        this.profileDao = profileDao;
        this.metricsPublisher = metricsPublisher;
    }

    public UpdateProfileResult handleRequest(final UpdateProfileRequest updateProfileRequest) {
        log.info("Received UpdateProfileRequest{}", updateProfileRequest);

        if(!MusicPlaylistServiceUtils.isValidString(updateProfileRequest.getFirstName())){
            publishExceptionMetrics(true,false);
            throw new InvalidAttributeValueException("Profile Name" + updateProfileRequest.getFirstName() + "cannot contain illegal characters");
        }
        Profile profile = profileDao.getProfile(updateProfileRequest.getId()

    }
}
