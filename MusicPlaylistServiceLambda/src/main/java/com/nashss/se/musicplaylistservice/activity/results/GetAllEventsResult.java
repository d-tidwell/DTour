package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.dynamodb.models.Event;

import java.util.ArrayList;
import java.util.List;

public class GetAllEventsResult {
    private final List<Event> allEventList;

    private GetAllEventsResult(List<Event> allEventList) {
        this.allEventList = allEventList;
    }

    public List<Event> getAllEventList() {
        return allEventList;
    }

    @Override
    public String toString() {
        return "GetAllEventsResult{" +
                "allEventList=" + allEventList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Event> allEventList;

        public Builder withEventList(List<Event> allEventList) {

            this.allEventList = new ArrayList<>(allEventList);
            return this;
        }

        public GetAllEventsResult build() {
            return new GetAllEventsResult(allEventList);
        }
    }
}