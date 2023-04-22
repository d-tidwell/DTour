package com.nashss.se.musicplaylistservice.converters;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nashss.se.musicplaylistservice.dynamodb.models.Event;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.models.EventModel;
import com.nashss.se.musicplaylistservice.models.ProfileModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    /**
     * Converts a provided {@link Profile} into a {@link ProfileModel} representation.
     *
     * @return the converted ProfileModel
     */
    private final Logger log = LogManager.getLogger();

    public ProfileModel toProfileModel(Profile profile){
        
        return ProfileModel.builder()
                .withProfileId(profile.getId())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName())
                .withLocation(profile.getLocation())
                .withGender(profile.getGender())
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
        .withName(event.getName())
        .withEventCreator(event.getEventCreator())
        .withEventAddress(event.getAddress())
        .withDescription(event.getDescription())
        .withDateTime(event.getDateTime())
        .withCategory(event.getCategory())
        .withAttendees(event.getAttendees())
        .build();
    }

}
