package com.nashss.se.musicplaylistservice.activity.results;

import com.nashss.se.musicplaylistservice.models.EventModel;
import com.nashss.se.musicplaylistservice.models.ProfileModel;

public class GetEventResult {

    private final EventModel eventModel;

    public GetEventResult(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    @Override
    public String toString() {
        return "GetEventResult{" +
                "eventModel=" + eventModel +
                '}';
    }

    public static class Builder{
        private EventModel eventModel;

        public GetEventResult.Builder withEventModel(EventModel eventModel){
            this.eventModel = eventModel;
            return this;
        }

        public GetEventResult build(){
            return new GetEventResult(eventModel);
        }

    }

    public static GetEventResult.Builder builder(){
        return new GetEventResult.Builder();
    }

}

