package com.nashss.se.musicplaylistservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.exceptions.InvalidAttributeException;
import com.nashss.se.musicplaylistservice.exceptions.InvalidBirthdateException;
import com.nashss.se.musicplaylistservice.exceptions.ProfileNotFoundException;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
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
    public boolean isValidBirthday(ZonedDateTime birthday) {
        // Get today's date in the same time zone as the birthday
        LocalDate today = LocalDate.now(birthday.getZone());

        // Check that the birthday is not in the future
        if (birthday.toLocalDate().isAfter(today)) {
            return false;
        }

        // Check that the person is less than 120 years old
        if (birthday.plusYears(120).toLocalDate().isBefore(today)) {
            return false;
        }

        return true;
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
        saveProfile.setId(id);
        if(isNew) {
            saveProfile.setFirstName(firstName);
            saveProfile.setLastName(lastName);
            saveProfile.setLocation(location);
            saveProfile.setGender(gender);

            if(isValidBirthday(dateOfBirth)) {
                saveProfile.setDateOfBirth(dateOfBirth.toString());
            } else {
                throw new InvalidBirthdateException("You are probably less than 120 years old and born before today.");
            }
            this.dynamoDbMapper.save(saveProfile);

        } else {
            Profile oldProfile = this.getProfile(id);
            saveProfile.setFollowing(oldProfile.getFollowing());
            saveProfile.setEvents(oldProfile.getEvents());
            if (firstName != null || !firstName.isEmpty()) {
                saveProfile.setFirstName(firstName);
            } 
            if (lastName != null || !lastName.isEmpty()) {
                saveProfile.setLastName(lastName);
            }
            if (location != null || !location.isEmpty()) {
                saveProfile.setLocation(location);
            }
            if (gender != null || !gender.isEmpty()) {
                saveProfile.setGender(gender);
            }
            if (!Objects.isNull(dateOfBirth)) {
                if(isValidBirthday(dateOfBirth)) {
                    saveProfile.setDateOfBirth(dateOfBirth.toString());
                } else {
                    throw new InvalidBirthdateException("You are probably less than 120 years old and born before today.");
                }
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

    public Set<String> removeProfileFromEvent(String id, String profileIdToRemove) {
        Profile profile = this.getProfile(id);
        Set<String> events = profile.getEvents();
        events.remove(profileIdToRemove);
        profile.setEvents(events);
        this.dynamoDbMapper.save(profile);
        return events;
    }
}
