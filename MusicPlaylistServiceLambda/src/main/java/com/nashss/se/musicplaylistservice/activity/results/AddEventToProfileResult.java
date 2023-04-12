package com.nashss.se.musicplaylistservice.activity.results;

import java.util.ArrayList;
import java.util.List;

public class AddEventToProfileResult {
    private final List<String> eventList;

    private AddEventToProfileResult(List<String> eventList) {
        this.eventList = eventList;
    }

    public List<String> getEventList() {
        return new ArrayList<>(eventList);
    }

    @Override
    public String toString() {
        return "AddEventToProfileResult{" +
                "eventList=" + eventList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> eventList;

        public Builder withEventList(List<String> eventList) {
            this.eventList = new ArrayList<>(eventList);
            return this;
        }

        public AddEventToProfileResult build() {
            return new AddEventToProfileResult(eventList);
        }
    }
}

