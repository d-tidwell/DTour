package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetAllEventsRequest.Builder.class)
public class GetAllEventsRequest {

    public GetAllEventsRequest() {
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {

        public GetAllEventsRequest build() {
            return new GetAllEventsRequest();}
    }


}