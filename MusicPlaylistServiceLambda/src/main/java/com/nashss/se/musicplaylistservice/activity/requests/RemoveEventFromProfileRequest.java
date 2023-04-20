package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder= RemoveEventFromProfileRequest.class)
public class RemoveEventFromProfileRequest {
    private final String profileId;
    private final String eventId;

    private RemoveEventFromProfileRequest(String profileId, String eventId){
        this.profileId = profileId;
        this.eventId = eventId;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getEventId() {
        return eventId;
    }

    @Override
    public String toString() {
        return "RemoveEventFromProfileRequest{" +
                "profileId='" + profileId + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder

    public static Builder builder(){
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder{

        private String profileId;
        private String eventId;

        public Builder withProfileId(String profileId){
            this.profileId = profileId;
            return this;
        }
        public Builder withEventId(String eventId){
            this.eventId = eventId;
            return this;
        }
        public RemoveEventFromProfileRequest build(){
            return new RemoveEventFromProfileRequest(profileId,eventId);
        }
    }
}
