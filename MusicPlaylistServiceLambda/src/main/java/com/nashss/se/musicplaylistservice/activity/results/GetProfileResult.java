package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

import java.util.Set;

public class GetProfileResult {

    private final ProfileModel profileModel;

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

        public GetProfileResult.Builder withProfileModel(String profileId, String firstName,
                                                         String lastName, String location,
                                                         String gender, String dateOfBirth,
                                                         Set<String> following, Set<String> events){
            this.profileModel = new ProfileModel(profileId, firstName, lastName, location, gender, dateOfBirth, following, events);
            return this;
        }

        public GetProfileResult build(){
            return new GetProfileResult(profileModel);
        }

    }

}
