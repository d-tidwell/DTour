package com.nashss.se.musicplaylistservice.converters;


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
       .withUserId(profile.getUserId())
       .withFollowers(profile.getFollowers())
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
        .withEventAddress(event.getEventAddress())
        .withEventType(event.getEventType())
        .withEventDate(event.getEventDate())
        .withEventTime(event.getEventTime())
        .withAttendees(event.getAttendees())
        .build();
    }
    /**
     * Converts a provided {@link User} into a {@link UserModel} representation.
     *
     * @param user the User to convert
     * @return the converted UserModel
     */
    public UserModel toUserModel(User user){

        return UserModel.builder()
        .withUserId(user.getUserId())
        .withFullName(user.getFullName())
        .withEmailAddress(user.getEmailAddress())
        .withGender(user.getGender())
        .withDateOfBirth(user.getDateOfBirth())
        .build();
    }

}
