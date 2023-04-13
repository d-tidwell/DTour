package com.nashss.se.musicplaylistservice.activity.results;
import com.nashss.se.musicplaylistservice.models.ProfileModel;


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

        public Builder withProfileModel(ProfileModel profileModel){
            this.profileModel = profileModel;
            return this;
        }

        public GetProfileResult build(){
            return new GetProfileResult(profileModel);
        }

    }

    public static Builder builder(){
        return new Builder();
    }

}
