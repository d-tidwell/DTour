package com.nashss.se.musicplaylistservice.activity.results;

import java.util.ArrayList;
import java.util.List;

public class AddProfileToFollowingResult {

    private final List<String> profileList;

    private AddProfileToFollowingResult(List<String> profileList) {
        this.profileList = profileList;
    }

    public List<String> getProfileList() {
        return profileList;
    }

    @Override
    public String toString() {
        return "AddProfileToFollowingResult{" +
                "profileList=" + profileList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new AddProfileToFollowingResult.Builder();
    }

    public static class Builder {
        private List<String> profileList;

        public Builder withProfileList(List<String> profileList) {
            this.profileList = new ArrayList<>(profileList);
            return this;
        }

        public AddProfileToFollowingResult build() {
            return new AddProfileToFollowingResult(profileList);
        }

    }
}

