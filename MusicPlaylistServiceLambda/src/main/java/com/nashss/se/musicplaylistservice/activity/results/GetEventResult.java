package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.EventModel;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

public class GetEventResult {

    private final EventModel eventModel;

    public GetProfileResult(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public ProfileModel getProfileModel() {
        return profileModel;
    }

    @Override
    public String toString() {
        return "GetProfileResult{" +
                "profileModel=" + profileModel +
                '}';
    }

    public static class Builder{
        private ProfileModel profileModel;

        public GetProfileResult.Builder withProfileModel(ProfileModel profileModel){
            this.profileModel = profileModel;
            return this;
        }

        public GetProfileResult build(){
            return new GetProfileResult(profileModel);
        }

    }

    public static GetProfileResult.Builder builder(){
        return new GetProfileResult.Builder();
    }

}

