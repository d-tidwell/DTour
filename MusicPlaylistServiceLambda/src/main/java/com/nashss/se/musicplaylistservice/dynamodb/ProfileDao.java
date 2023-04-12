package com.nashss.se.musicplaylistservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.musicplaylistservice.exceptions.ProfileNotFoundException;
import com.nashss.se.musicplaylistservice.metrics.MetricsConstants;
import com.nashss.se.musicplaylistservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
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

    public Profile saveProfile(String profileId, String firstName, String lastName, String location, String gender, ZonedDateTime dateOfBirth, Set<String> events, Set<String> following ) {
        Profile profile = new Profile();

        if(profileId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || location.isEmpty() || gender.isEmpty() || dateOfBirth==null){
            throw new InvalidAttributeValueException("Arguments can not be empty, please try again.");
        }

        if(events == null){
            events = new HashSet<>();
        }
        if(following == null){
            following = new HashSet<>();
        }

        return profile;
    }


    public Profile saveProfile(String profileId, String firstName, String lastName, String location, String gender,
                               String dateOfBirth, Set<String> events, Set<String> following) {
        Profile profile = new Profile();
        profile.setId(profileId);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setLocation(location);
        profile.setGender(gender);
        profile.setDateOfBirth(dateOfBirth);
        profile.setFollowing(following);
        profile.setEvents(events);
        this.dynamoDbMapper.save(profile);
        return profile;
    }

    public List<String>  addProfileToFollowersList(String id, String profileToAdd) {
        List<String> updatedListAfterAdding = new ArrayList<>();
        if (id.isEmpty() || id == null) {
            throw new ProfileNotFoundException("The entered email address is invalid. Please try again.");
        }
        Profile profile = getProfile(id);
        if (profile == null) {
            throw new ProfileNotFoundException("Profile does not exist. Please enter another emailAddress.");
        }
        Set<String> following = profile.getFollowing();
        if (following == null) {
            following = new HashSet<>();
        }
        following.add(profileToAdd);
        profile.setFollowing(following);
        saveProfile(profile.getId(), profile.getFirstName(), profile.getLastName(), profile.getLocation(),
                profile.getGender(), profile.getDateOfBirth(), profile.getEvents(), profile.getFollowing ());

        updatedListAfterAdding.addAll(following);

        return updatedListAfterAdding;
    }

    public List<String> removeProfileFromFollowing(String id, String profileIdToRemove) {
        List<String> updatedList = new ArrayList<>();
        Profile profile = getProfile(id);
        if (profile == null) {
            throw new ProfileNotFoundException("Unable to retrieve the profile with the given id.");
        }
        Set<String> following = profile.getFollowing();
        if (following == null || !(following.contains(id))) {
            throw new ProfileNotFoundException("Profile with the given id is not your following.");
        }

        following.remove(profileIdToRemove);
        profile.setFollowing(following);
        saveProfile(profile.getId(), profile.getFirstName(), profile.getLastName(), profile.getLocation(),
                profile.getGender(), profile.getDateOfBirth(), profile.getEvents(), profile.getFollowing());

        updatedList.addAll(following);

        return updatedList;
    }

}
