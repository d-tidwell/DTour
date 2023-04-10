package com.nashss.se.musicplaylistservice.models;

import java.util.List;
import java.util.Objects;

public class ProfileModel {

    private final String profileId;
    private final String firstName;
    private final String lastName;
    private final String location;
    private final String gender;
    private final String dateOfBirth;
    private final List<String> following;
    private final List<String> events;

    public ProfileModel(String profileId, String firstName,
                        String lastName, String location,
                        String gender, String dateOfBirth,
                        List<String> following, List<String> events) {
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

    public List<String> getFollowing() {
        return following;
    }

    public List<String> getEvents() {
        return events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileModel that = (ProfileModel) o;
        return profileId.equals(that.profileId) && following.equals(that.following) && events.equals(that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, following, events);
    }

    public static PlaylistModel.Builder builder() {
        return new PlaylistModel.Builder();
    }

    public static class Builder{
        private String userId;
        private String firstName;
        private String lastName;
        private String location;
        private String gender;
        private String dateOfBirth;
        private List<String> following;
        private List<String> events;

        public Builder withProfileId(String userId){
            this.userId = userId;
            return this;
        }

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
            this.dateOfBirth= dateOfBirth;
            return this;
        }

        public Builder withFollowing(List<String> following){
            this.following = following;
            return this;
        }

        public Builder withEvents(List<String> events){
            this.events = events;
            return this;
        }

        public ProfileModel build() {return new ProfileModel(userId, firstName,
                lastName, location,gender, dateOfBirth, following, events);}
    }
}
