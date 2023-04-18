package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = RemoveFollowingFromProfileRequest.Builder.class)
public class RemoveFollowingFromProfileRequest {
    private final String id;
    private final String profileIdToRemove;

    private RemoveFollowingFromProfileRequest(String id, String profileIdToRemove) {
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

        public RemoveFollowingFromProfileRequest build() {
            return new RemoveFollowingFromProfileRequest(id, profileIdToRemove);
        }
    }
}