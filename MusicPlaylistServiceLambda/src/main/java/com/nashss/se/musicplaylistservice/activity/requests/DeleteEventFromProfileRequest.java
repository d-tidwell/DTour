package com.nashss.se.musicplaylistservice.activity.requests;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


@JsonDeserialize(builder = DeleteEventFromProfileRequest.Builder.class)
public class DeleteEventFromProfileRequest {

    private final String profileId;
    private final String eventId;


    public DeleteEventFromProfileRequest(String profileId, String eventId) {
        this.profileId = profileId;
        this.eventId = eventId;

    }

    public String getEventId() {
        return eventId;
    }
    public String getProfileId() {return profileId;}

    @Override
    public String toString() {
        return "DeleteEventFromProfileRequest{" +
                "profileId='" + profileId + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }

    @JsonPOJOBuilder
    public static class Builder {

        private String profileId;
        private String eventId;

        public Builder withProfileId(String profileId) {
            this.profileId = profileId;
            return this;
        }

        public Builder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public DeleteEventFromProfileRequest build() {
            return new DeleteEventFromProfileRequest(profileId, eventId);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

}