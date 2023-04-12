package com.nashss.se.musicplaylistservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
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

//        ?? NOAH We have two save profile functions both different neither correct
//    public Profile saveProfile(String profileId, String firstName, String lastName, String location, String gender, ZonedDateTime dateOfBirth, Set<String> events, Set<String> following ) {
//        Profile profile = new Profile();
//        //??where are you setting these values and saving them
//        if(profileId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || location.isEmpty() || gender.isEmpty() || dateOfBirth==null){
//            //fix this exception so that it is throwable
//            throw new InvalidAttributeValueException("Arguments can not be empty, please try again.");
//        }
//
//        if(events == null){
//            events = new HashSet<>();
//        }
//        if(following == null){
//            following = new HashSet<>();
//        }
//
//        return profile;
//    }

//    ?? NATALIA We have two save profile functions both different neither correct
//    public Profile saveProfile(String profileId, String firstName, String lastName, String location, String gender,
//                               String dateOfBirth, Set<String> events, Set<String> following) {
        //what happens if this is an update and not a save ? what about null values??
//        Profile profile = new Profile();
//        profile.setId(profileId);
//        profile.setFirstName(firstName);
//        profile.setLastName(lastName);
//        profile.setLocation(location);
//        profile.setGender(gender);
//        //this needs to be a zoned datetime object
//        profile.setDateOfBirth(dateOfBirth);
//        //what if this is an empty value???
//        profile.setFollowing(following);
//        profile.setEvents(events);
//        this.dynamoDbMapper.save(profile);
//        return profile;
//    }
    public Profile saveProfile(boolean isNew, String emailAddress, String firstName, String lastName, String location, String gender, ZonedDateTime dateOfBirth) {
        Profile saveProfile = new Profile();
        //needed either for update or save bc its the hashkey
        saveProfile.setId(emailAddress);
        //if this is a new profile we are saving just save the info we are given we know this bc we passed true
        if(isNew) {
            saveProfile.setFirstName(firstName);
            saveProfile.setLastName(lastName);
            saveProfile.setLocation(location);
            saveProfile.setGender(gender);
            //this needs to be a zoned datetime object
            saveProfile.setDateOfBirth(dateOfBirth.toString());
            //they couldn't possibly have values so we need to set them here so the field exists
            saveProfile.setEvents(new HashSet<>());
            saveProfile.setFollowing(new HashSet<>());
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
            if (!Objects.isNull(dateOfBirth)) {
                saveProfile.setDateOfBirth(dateOfBirth.toString());
            }
            this.dynamoDbMapper.save(saveProfile);
        }
        return saveProfile;
    }
    public List<String>  addProfileToFollowersList(String id, String profileToAdd) {
        List<String> updatedListAfterAdding = new ArrayList<>();

        //how can you throw a profile not found exception when you havent checked if it exists???? What you have written here says
        // if the string is empty I guess that means it doesn't exist which is incorrect you have to check with getProfile
        //if you are just checking for empty strings it would be an invalideattribute exception
        if (id.isEmpty() || id == null) {
            throw new ProfileNotFoundException("The entered email address is invalid. Please try again.");
        }
        Profile profile = getProfile(id);
        if (profile == null) {
            throw new ProfileNotFoundException("Profile does not exist. Please enter another emailAddress.");
        }
        Set<String> following = profile.getFollowing();
        //this is necessary bc the saveProfile initializes this on creation or the profile doesn't exist which
        //would have been caught above
        if (following == null) {
            following = new HashSet<>();
        }
        //you need to check if the profile you are adding also exists like you did above with id before adding it
        following.add(profileToAdd);
        profile.setFollowing(following);
        //this needs to be changed to reflect the saveProfile implementation that has been corrected
        //or just call the dynamoDBmapper save method that will just update one field that you need to
        saveProfile(profile.getId(), profile.getFirstName(), profile.getLastName(), profile.getLocation(),
                profile.getGender(), profile.getDateOfBirth(), profile.getEvents(), profile.getFollowing ());

        //why can't you just return following instead of a whole new object??
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

    public Set<String> addEventToFollowing(String eventId, String profileId) {
        Profile profileToAddEventTo = this.getProfile(profileId);
        Set<String> eventsStoredAlready = profileToAddEventTo.getEvents();
        eventsStoredAlready.add(eventId);
        profileToAddEventTo.setEvents(eventsStoredAlready);
        //??? make sure this doesn't overwrite the existing fields in the database object its late and I can't
        //remember off the top of my head
        this.dynamoDbMapper.save( profileToAddEventTo);
        return eventsStoredAlready;
    }
}
