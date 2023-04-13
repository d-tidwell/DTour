package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddProfileToFollowingRequest.Builder.class)
public class AddProfileToFollowingRequest {
    private final String id;
    private final String idToAdd;

    private AddProfileToFollowingRequest(String id, String idToAdd) {
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

        public AddProfileToFollowingRequest build() {
            return new AddProfileToFollowingRequest(id, idToAdd);
        }
    }
}

