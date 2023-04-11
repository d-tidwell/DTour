package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class RemoveFromFollowingResult {
    private final List<ProfileModel> profileModelList;

    private RemoveFromFollowingResult (List<ProfileModel> profileModelList) {
        this.profileModelList = profileModelList;
    }

    public List<ProfileModel> getProfileModelList() {
        return profileModelList;
    }

    @Override
    public String toString() {
        return "RemoveFromFollowingResult{" +
                "profileModelList=" + profileModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static RemoveFromFollowingResult.Builder builder() {
        return new RemoveFromFollowingResult.Builder();
    }

    public static class Builder {
        private List<ProfileModel> profileModelList;

        public RemoveFromFollowingResult.Builder withProfileModelList(List <ProfileModel> profileModelList) {
            this.profileModelList = new ArrayList<>(profileModelList);
            return this;
        }

        public RemoveFromFollowingResult build() {
            return new RemoveFromFollowingResult(profileModelList);
        }

    }
}


