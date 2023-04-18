package com.nashss.se.musicplaylistservice.activity.requests;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


@JsonDeserialize(builder = GetEventRequest.Builder.class)
public class GetEventRequest {
    private final String eventId;
    private final String dateTime;


    public GetEventRequest(String eventId,String dateTime) {
        this.eventId = eventId;
        this.dateTime = dateTime;
    }

    public String getEventId() {
        return eventId;
    }


    @Override
    public String toString() {
        return "GetEventRequest{" +
                "eventId='" + eventId + '\'' +
                '}';
    }
    @JsonPOJOBuilder
    public static class Builder {

        private String eventId;


        public Builder withId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public GetEventRequest build() {
            System.out.println(eventId);
            return new GetEventRequest(eventId);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

}