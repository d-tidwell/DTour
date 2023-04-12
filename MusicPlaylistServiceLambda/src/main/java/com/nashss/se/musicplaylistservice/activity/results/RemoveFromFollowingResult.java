package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class RemoveFromFollowingResult {

    private final List<String> profileList;

    private RemoveFromFollowingResult (List<String> profileList) {
        this.profileList = profileList;
    }

    public List<String> getProfileList() {
        return profileList;
    }

    @Override
    public String toString() {
        return "RemoveFromFollowingResult{" +
                "profileList=" + profileList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static RemoveFromFollowingResult.Builder builder() {
        return new RemoveFromFollowingResult.Builder();
    }

    public static class Builder {
        private List<String> profileList;

        public RemoveFromFollowingResult.Builder withProfileList(List<String> profileList) {
            this.profileList = new ArrayList<>(profileList);
            return this;
        }

        public RemoveFromFollowingResult build() {
            return new RemoveFromFollowingResult(profileList);
        }

    }
}


