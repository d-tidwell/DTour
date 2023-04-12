package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = RemoveFromFollowingRequest.Builder.class)
public class RemoveFromFollowingRequest {
    private final String id;
    private final String profileIdToRemove;

    private RemoveFromFollowingRequest(String id, String profileIdToRemove) {
        this.id = id;
        this.profileIdToRemove = profileIdToRemove;
    }

    public String getId() {
        return id;
    }

    public String getProfileIdToRemove() {
        return profileIdToRemove;
    }

    @Override
    public String toString() {
        return "RemoveFromFollowingRequest{" +
                "id='" + id + '\'' +
                ", profileIdToRemove='" + profileIdToRemove + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String profileIdToRemove;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }
        public Builder withProfileIdToRemove(String profileIdToRemove) {
            this.profileIdToRemove = profileIdToRemove;
            return this;
        }

        public RemoveFromFollowingRequest build() {
            return new RemoveFromFollowingRequest(id, profileIdToRemove);
        }
    }
}