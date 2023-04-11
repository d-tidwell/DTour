package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = com.nashss.se.musicplaylistservice.activity.requests.AddSongToPlaylistRequest.Builder.class)
public class AddProfileToFollowingRequest {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String location;
    private final String gender;
    private final String dateOfBirth;

    private AddProfileToFollowingRequest(String id, String firstName, String lastName, String location, String gender, String dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "AddProfileToFollowingRequest{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AddProfileToFollowingRequest.Builder builder() {
        return new AddProfileToFollowingRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private String location;
        private String gender;
        private String dateOfBirth;

        public AddProfileToFollowingRequest.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public AddProfileToFollowingRequest.Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AddProfileToFollowingRequest.Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AddProfileToFollowingRequest.Builder withLocation(String location) {
            this.location = location;
            return this;
        }

        public AddProfileToFollowingRequest.Builder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public AddProfileToFollowingRequest.Builder withDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public AddProfileToFollowingRequest build() {
            return new AddProfileToFollowingRequest(id, firstName, lastName, location, gender, dateOfBirth);
        }
    }
}

