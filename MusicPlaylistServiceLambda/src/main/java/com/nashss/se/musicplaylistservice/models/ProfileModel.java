package com.nashss.se.musicplaylistservice.models;

import java.util.List;
import java.util.Objects;

public class ProfileModel {

    private final String UserId;
    private final List<String> following;
    private final List<String> events;

    private ProfileModel(String userId, List<String> following, List<String> events) {
        UserId = userId;
        this.following = following;
        this.events = events;
    }

    public String getUserId() {
        return UserId;
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
        return UserId.equals(that.UserId) && following.equals(that.following) && events.equals(that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserId, following, events);
    }

    public static PlaylistModel.Builder builder() {
        return new PlaylistModel.Builder();
    }

    public static class Builder{
        private String userId;
        private List<String> following;
        private List<String> events;

        public Builder withUserId(String id){
            this.userId = id;
            return this;
        }

        public Builder withFollowing(List following){
            this.following = following;
            return this;
        }

        public Builder withEvents(List events){
            this.events = events;
            return this;
        }

        public ProfileModel build() {return new ProfileModel(userId, following, events);}
    }
}
