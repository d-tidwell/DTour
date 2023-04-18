package com.nashss.se.musicplaylistservice.activity.results;

import java.util.ArrayList;
import java.util.List;

public class RemoveFollowingFromProfileResult {

    private final List<String> profileList;

    private RemoveFollowingFromProfileResult(List<String> profileList) {
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
    public static RemoveFollowingFromProfileResult.Builder builder() {
        return new RemoveFollowingFromProfileResult.Builder();
    }

    public static class Builder {
        private List<String> profileList;

        public RemoveFollowingFromProfileResult.Builder withProfileList(List<String> profileList) {
            this.profileList = new ArrayList<>(profileList);
            return this;
        }

        public RemoveFollowingFromProfileResult build() {
            return new RemoveFollowingFromProfileResult(profileList);
        }

    }
}


