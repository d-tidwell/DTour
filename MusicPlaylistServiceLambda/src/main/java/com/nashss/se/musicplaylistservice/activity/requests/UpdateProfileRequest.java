package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateProfileRequest.Builder.class)
public class UpdateProfileRequest {
    private final String firstName;
    private final String lastName;
    private final String location;
    private final String gender;
    private final String dateOfBirth;
    private final String profileId;

    private UpdateProfileRequest(String firstName, String lastName, String location, String gender, String dateOfBirth,String profileId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.profileId = profileId;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
    public String getProfileId(){
        return profileId;
    }

    @Override
    public String toString() {
        return "UpdateProfileRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + profileId + '\'' +
                '}';
    }
    public static Builder builder(){
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder{
        private String firstName;
        private String lastName;
        private String location;
        private String gender;
        private String dateOfBirth;
        private String profileId;

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public Builder withLastName(String lastName){
            this.lastName = lastName;
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
        public Builder withId(String profileId){
            this.profileId = profileId;
            return this;
        }
        public UpdateProfileRequest build(){
            return new UpdateProfileRequest(firstName,lastName,location,gender,dateOfBirth,profileId);
        }
    }
}
