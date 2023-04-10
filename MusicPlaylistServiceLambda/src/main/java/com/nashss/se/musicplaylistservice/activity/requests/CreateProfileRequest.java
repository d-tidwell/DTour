package com.nashss.se.musicplaylistservice.activity.requests;

import java.time.ZonedDateTime;
import java.util.Set;

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

    public boolean isNew() {
        return isNew;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getLocation() {
        return location;
    }

    public String getGender() {
        return gender;
    }

    public ZonedDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public Set<Event> getEventList() {
        return eventList;
    }

    public Set<String> getFollowerList() {
        return followerList;
    }

    private final Set<String> followerList;

    @Override
    public String toString() {
        return "CreateProfileRequest{" +
                "isNew=" + isNew +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", eventList=" + eventList +
                ", followerList=" + followerList +
                '}';
    }

    private CreateProfileRequest(boolean isNew, String firstName, String lastName, String emailAddress, String location,
                                 String gender, ZonedDateTime dateOfBirth, Set<Event> eventList, Set<String> followerList){
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
@JsonPOJOBuilder
    public static class Builder{
    private  boolean isNew;
    private  String firstName;
    private  String lastName;
    private  String emailAddress;
    private  String location;
    private  String gender;
    private  ZonedDateTime dateOfBirth;
    private  Set<Event> eventList;

    public Builder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    public Builder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }
    public Builder withEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
        return this;
    }
    public Builder withLocation(String location){
        this.location = location;
        return this;
    }
    public Builder withGender(String gender){
        this.gender = gender;
        return this;
    }
    public Builder withDateOfBirth(ZonedDateTime dateOfBirth){
        this.dateOfBirth = dateOfBirth;
        return this;
    }
    public Builder withEvents(Set<Event> eventList){
        this.eventList = eventList;
        return this;
    }


}

    
}
