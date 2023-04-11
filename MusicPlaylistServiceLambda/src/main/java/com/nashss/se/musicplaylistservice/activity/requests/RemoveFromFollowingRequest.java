package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = RemoveFromFollowingRequest.Builder.class)
public class RemoveFromFollowingRequest {
    private final String id;

    private RemoveFromFollowingRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RemoveFromFollowingRequest{" +
                "id='" + id;
    }

    //CHECKSTYLE:OFF:Builder
    public static RemoveFromFollowingRequest.Builder builder() {
        return new RemoveFromFollowingRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;

        public RemoveFromFollowingRequest.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public RemoveFromFollowingRequest build() {
            return new RemoveFromFollowingRequest(id);
        }
    }
}