package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.activity.requests.AddEventToProfileRequest;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class AddProfileToFollowingResult {

//    private final List<ProfileModel> profileModelList;
    private final List<String> profileModelList;

//    private AddProfileToFollowingResult(List<ProfileModel> profileModelList) {
//        this.profileModelList = profileModelList;
//    }
    private AddProfileToFollowingResult(List<String> profileModelList) {
        this.profileModelList = profileModelList;
    }

//    public List<ProfileModel> getProfileModelList() {
//        return profileModelList;
//    }
    public List<String> getProfileModelList() {return profileModelList;}

    @Override
    public String toString() {
        return "AddProfileToFollowingResult{" +
                "profileModelList=" + profileModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new AddProfileToFollowingResult.Builder();
    }

    public static class Builder {
//        private List<ProfileModel> profileModelList;
        private List<String> profileModelList;

//        public AddProfileToFollowingResult.Builder withProfileModelListList(List <ProfileModel> profileModelList) {
//            this.profileModelList = new ArrayList<>(profileModelList);
//            return this;
//        }
        public Builder withProfileModelList(List<String> updatedListProfiles) {
            this.profileModelList = updatedListProfiles;
            return this;
        }

        public AddProfileToFollowingResult build() {
            return new AddProfileToFollowingResult(profileModelList);
        }


    }
}

