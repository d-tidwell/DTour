package com.nashss.se.musicplaylistservice.converters;


import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    /**
     * Converts a provided {@link Profile} into a {@link ProfileModel} representation.
     *
     * @param profile the Profile to convert
     * @return the converted ProfileModel
     */

    public ProfileModel toProfileModel(Profile profile){

       return ProfileModel.builder()
               .withProfileId(profile.getProfileId())
               .withFirstName(profile.getFirstName())
               .withLastName(profile.getLastName())
               .withGender(profile.getGender())
               .withLocation(profile.getLocation())
               .withDateOfBirth(profile.getDateOfBirth())
               .withFollowing(profile.getFollowing())
               .withEvents(profile.getEvents())
               .build();
    }
   
    /**
     * Converts a provided {@link Event} into a {@link EventModel} representation.
     *
     * @param event the Event to convert
     * @return the converted EventModel
     */
    
    public EventModel toEventModel(Event event){

        return EventModel.builder()
        .withEventId(event.getEventId())
        .withEventName(event.getEventName())
        .withEventCreator(event.getEventCreator())
        .withEventAddress(event.getEventAddress())
        .withDescription(event.getDescription())
        .withEventDateTime(event.getEventDateTime())
        .withEventCategory(event.getEventCategory())
        .withAttendees(event.getAttendees())
        .build();
    }

}
