package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddFollowingToProfileRequest.Builder.class)
public class AddFollowingToProfileRequest {
    private final String id;
    private final String idToAdd;

    private AddFollowingToProfileRequest(String id, String idToAdd) {
        this.id = id;
        this.idToAdd = idToAdd;
    }

    public String getId() {
        return id;
    }

    public String getIdToAdd() {
        return idToAdd;
    }

    @Override
    public String toString() {
        return "AddProfileToFollowingRequest{" +
                "id='" + id + '\'' +
                ", idToAdd='" + idToAdd + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String idToAdd;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withIdToAdd(String idToAdd) {
            this.idToAdd = idToAdd;
            return this;
        }

        public AddFollowingToProfileRequest build() {
            return new AddFollowingToProfileRequest(id, idToAdd);
        }
    }
}

