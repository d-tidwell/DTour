package com.nashss.se.musicplaylistservice.activity.results;

import java.util.ArrayList;
import java.util.List;

public class RemoveEventFromProfileResult {
    private final List<String> eventList;

    private RemoveEventFromProfileResult(List<String> eventList) {
        this.eventList = eventList;
    }

    public List<String> getProfileList() {
        return eventList;
    }

    @Override
    public String toString() {
        return "RemoveFromEventResult{" +
                "profileList=" + eventList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static RemoveEventFromProfileResult.Builder builder() {
        return new RemoveEventFromProfileResult.Builder();
    }

    public static class Builder {
        private List<String> eventList;

        public Builder withEventList(List<String> eventList) {
            this.eventList = new ArrayList<>(eventList);
            return this;
        }

        public RemoveEventFromProfileResult build() {
            return new RemoveEventFromProfileResult(eventList);
        }

    }
}
