package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.EventModel;
import com.nashss.se.musicplaylistservice.models.SongModel;

import java.util.ArrayList;
import java.util.List;

public class AddEventToProfileResult {
    private final List<EventModel> eventList;

    private AddEventToProfileResult(List<EventModel> eventList) {
        this.eventList = eventList;
    }

    public List<EventModel> getEventList() {
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
        private List<EventModel> eventList;

        public Builder withEventList(List<EventModel> eventList) {
            this.eventList = new ArrayList<>(eventList);
            return this;
        }

        public AddEventToProfileResult build() {
            return new AddEventToProfileResult(eventList);
        }
    }
}

