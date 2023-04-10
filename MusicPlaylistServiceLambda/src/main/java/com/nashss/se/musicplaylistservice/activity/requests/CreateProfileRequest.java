package com.nashss.se.musicplaylistservice.activity.requests;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateProfileRequest.Builder.class)
public class CreateProfileRequest {
    private final boolean isNew;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String location;
    private final String gender;
    private final ZonedDateTime dateOfBirth;
    private final Set<Event> eventList;
    private final Set<String> followerList;

    private CreateProfileRequest(boolean isNew, String firstName, String lastName, String emailAddress,String location
    String gender, ZonedDateTime dateOfBirth,Set<Event> eventList,Set<String> followerList){
        this.isNew = isNew;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.eventList = eventList;
        this.followerList = followerList;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }

    
}
