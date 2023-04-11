package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddProfileToFollowingRequest.Builder.class)
public class AddProfileToFollowingRequest {
    private final String id;

    private AddProfileToFollowingRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AddProfileToFollowingRequest{" +
                "id='" + id;
    }

    //CHECKSTYLE:OFF:Builder
    public static AddProfileToFollowingRequest.Builder builder() {
        return new AddProfileToFollowingRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;

        public AddProfileToFollowingRequest.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public AddProfileToFollowingRequest build() {
            return new AddProfileToFollowingRequest(id);
        }
    }
}

