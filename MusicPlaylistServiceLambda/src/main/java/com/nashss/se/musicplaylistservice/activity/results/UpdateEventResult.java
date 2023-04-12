package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.EventModel;

public class UpdateEventResult {

    private final EventModel eventModel;

    public UpdateEventResult(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    @Override
    public String toString() {
        return "UpdateEventResult{" +
                "eventModel=" + eventModel +
                '}';
    }

    public static class Builder{
        private EventModel eventModel;

        public Builder withEvent(EventModel eventModel){
            this.eventModel = eventModel;
            return this;
        }

        public UpdateEventResult build(){
            return new UpdateEventResult(eventModel);
        }

    }

    public static Builder builder(){
        return new Builder();
    }

}