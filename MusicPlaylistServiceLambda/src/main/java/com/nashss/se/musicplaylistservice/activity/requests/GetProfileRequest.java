package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nashss.se.musicplaylistservice.activity.results.GetProfileResult;

import java.util.Set;

@JsonDeserialize(builder = GetProfileResult.Builder.class)
public class GetProfileRequest {
    private final String profileId;

    public GetProfileRequest(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileId() {
        return profileId;
    }


    @Override
    public String toString() {
        return "GetProfileRequest{" +
                "profileId='" + profileId + '\'' +
                '}';
    }

    public static class Builder{

        private String profileId;


        public Builder withId(String profileId) {
            this.profileId = profileId;
            return this;
        }

        public GetProfileRequest build(){
            return new GetProfileRequest(profileId);
        }

    }

    public static Builder builder(){
        return new Builder();
    }

}
