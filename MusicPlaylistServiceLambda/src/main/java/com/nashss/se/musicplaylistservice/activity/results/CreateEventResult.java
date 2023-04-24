package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.EventModel;

public class CreateEventResult {

    private final EventModel event;

    private CreateEventResult(EventModel event) {
        this.event = event;
    }

    public EventModel getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "CreateEventResult{" +
                "eventModel=" + event +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private EventModel event;

        public Builder withEvent(EventModel event) {
            this.event = event;
            return this;
        }

        public CreateEventResult build() {
            return new CreateEventResult(event);
        }
    }
}
