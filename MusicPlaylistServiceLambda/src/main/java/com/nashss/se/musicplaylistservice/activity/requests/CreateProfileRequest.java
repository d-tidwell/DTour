package com.nashss.se.musicplaylistservice.activity.requests;

import java.time.ZonedDateTime;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nashss.se.musicplaylistservice.dynamodb.models.Event;

@JsonDeserialize(builder = CreateProfileRequest.Builder.class)
public class CreateProfileRequest {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String location;
    private final String gender;
    private final String dateOfBirth;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }


    @Override
    public String toString() {
        return "CreateProfileRequest{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    private CreateProfileRequest(String firstName, String lastName, String id, String location,
                                 String gender, String dateOfBirth){

        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
    public static Builder builder() {
        return new Builder();
    }
@JsonPOJOBuilder
    public static class Builder{

    private  String id;
    private  String firstName;
    private  String lastName;
    private  String location;
    private  String gender;
    private  String dateOfBirth;


    public Builder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    public Builder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }
    public Builder withId(String id){
        this.id = id;
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
    public Builder withDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public CreateProfileRequest build(){

        return new CreateProfileRequest(firstName,lastName,id,location,gender,dateOfBirth);
    }
}
}
