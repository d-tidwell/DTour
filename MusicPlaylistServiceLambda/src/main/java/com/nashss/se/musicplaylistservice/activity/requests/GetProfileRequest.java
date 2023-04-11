package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nashss.se.musicplaylistservice.activity.results.GetProfileResult;

import java.util.Set;

@JsonDeserialize(builder = GetProfileResult.Builder.class)
public class GetProfileRequest {

    private final String profileId;
    private final String firstName;
    private final String lastName;
    private final String location;
    private final String gender;
    private final String dateOfBirth;
    private final Set<String> following;
    private final Set<String> events;

    public GetProfileRequest(String profileId, String firstName,
                             String lastName, String location,
                             String gender, String dateOfBirth,
                             Set<String> following, Set<String> events) {
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.following = following;
        this.events = events;
    }

    public String getProfileId() {
        return profileId;
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

    public Set<String> getFollowing() {
        return following;
    }

    public Set<String> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return "GetProfileRequest{" +
                "profileId='" + profileId + '\'' +
                '}';
    }

    public static class Builder{

        private String profileId;
        private String firstName;
        private String lastName;
        private String location;
        private String gender;
        private String dateOfBirth;
        private Set<String> following;
        private Set<String> events;

        public GetProfileRequest.Builder withId(String profileId) {
            this.profileId = profileId;
            return this;
        }

        public GetProfileRequest build(){
            return new GetProfileRequest(profileId, firstName, lastName, location,
                    gender, dateOfBirth, following, events);
        }

    }

    public static Builder builder(){
        return new Builder();
    }

}
