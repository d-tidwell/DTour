package com.nashss.se.musicplaylistservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.exceptions.InvalidAttributeException;
import com.nashss.se.musicplaylistservice.exceptions.ProfileNotFoundException;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Singleton
public class ProfileDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    @Inject
    public ProfileDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    public Profile getProfile(String id){

        Profile profile = this.dynamoDbMapper.load(Profile.class, id);

        if(profile == null){
            metricsPublisher.addCount(MetricsConstants.GETPROFILE_PROFILENOTFOUND_COUNT, 1);
            throw new ProfileNotFoundException("Could not find profile with profileId " + id);
        }
        metricsPublisher.addCount(MetricsConstants.GETPROFILE_PROFILENOTFOUND_COUNT, 0);

        return profile;
    }

    public Profile saveProfile(boolean isNew, String id, String firstName, String lastName, String location, String gender, ZonedDateTime dateOfBirth) {
        Profile saveProfile = new Profile();
        //needed either for update or save bc its the hashkey
        saveProfile.setId(id);
        //if this is a new profile we are saving just save the info we are given we know this bc we passed true
        if(isNew) {
            saveProfile.setFirstName(firstName);
            saveProfile.setLastName(lastName);
            saveProfile.setLocation(location);
            saveProfile.setGender(gender);
            //this needs to be a zoned datetime object to check for valid birthday but stored as a string
            //so you would need to make a function that does that
            saveProfile.setDateOfBirth(dateOfBirth.toString());
            //they couldn't possibly have values so we need to set them here so the field exists
//            saveProfile.setEvents(new HashSet<String>());
//            saveProfile.setFollowing(new HashSet<String>());
            this.dynamoDbMapper.save(saveProfile);

        //if the boolean is false it means we are updating and need to check each field to see if it needs updating
        } else {
            if (firstName != null || !gender.isEmpty()) {
                saveProfile.setFirstName(firstName);
            }
            if (lastName != null || !gender.isEmpty()) {
                saveProfile.setFirstName(lastName);
            }
            if (location != null || !gender.isEmpty()) {
                saveProfile.setFirstName(location);
            }
            if (gender != null || !gender.isEmpty()) {
                saveProfile.setFirstName(gender);
            }
            //this needs to be a zoned datetime object to check for valid birthday but stored as a string
            //so you would need to make a function that does that
            if (!Objects.isNull(dateOfBirth)) {
                saveProfile.setDateOfBirth(dateOfBirth.toString());
            }
            this.dynamoDbMapper.save(saveProfile);
        }
        return saveProfile;
    }
    public Set<String>  addProfileToFollowersList(String id, String profileToAdd) {
        if (id == null || id.isEmpty()) {
            throw new InvalidAttributeException("The entered email address is invalid. Please try again.");
        }

        Profile profile = getProfile(id);

        if (profileToAdd == null || profileToAdd.isEmpty()) {
            throw new InvalidAttributeException("The profile to add is invalid. Please try again.");
        }

        Profile profileToAddProfile = getProfile(profileToAdd);

        Set<String> following = profile.getFollowing();

        following.add(profileToAddProfile.getId());
        profile.setFollowing(following);
        this.dynamoDbMapper.save(profile);

        return new HashSet<>(following);
    }


    public Set<String> removeProfileFromFollowing(String id, String profileIdToRemove) {
        if (id == null || id.isEmpty()) {
            throw new InvalidAttributeException("The entered email address is invalid. Please try again.");
        }
        Profile profile = getProfile(id);
        getProfile(profileIdToRemove);
        Set<String> following = profile.getFollowing();
        following.remove(profileIdToRemove);
        profile.setFollowing(following);
        this.dynamoDbMapper.save(profile);
        Set<String> updatedList = new HashSet<>(following);
        return updatedList;

    }

    public Set<String> addEventToFollowing(String eventId, String profileId) {
        Profile profileToAddEventTo = this.getProfile(profileId);
        Set<String> eventsStoredAlready = profileToAddEventTo.getEvents();
        eventsStoredAlready.add(eventId);
        profileToAddEventTo.setEvents(eventsStoredAlready);
        this.dynamoDbMapper.save( profileToAddEventTo);
        return eventsStoredAlready;
    }
}
