package com.nashss.se.musicplaylistservice.activity.results;

import java.util.ArrayList;
import java.util.List;

public class AddFollowingToProfileResult {

    private final List<String> profileList;

    private AddFollowingToProfileResult(List<String> profileList) {
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
        return new Builder();
    }

    public static class Builder {
        private List<String> profileList;

        public Builder withProfileList(List<String> profileList) {
            this.profileList = new ArrayList<>(profileList);
            return this;
        }

        public AddFollowingToProfileResult build() {
            return new AddFollowingToProfileResult(profileList);
        }

    }
}

