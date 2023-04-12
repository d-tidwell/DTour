package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Set;

@JsonDeserialize(builder = CreateEventRequest.Builder.class)
public class CreateEventRequest {

    private final String name;
    private final String eventCreator;
    private final String address;
    private final String description;
    private final String dateTime;
    private final Set<String> category;

    private CreateEventRequest(String name, String eventCreator, String address,
                               String description, String dateTime, Set<String> category) {
        this.name = name;
        this.eventCreator = eventCreator;
        this.address = address;
        this.description = description;
        this.dateTime = dateTime;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getEventCreator() {
        return eventCreator;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Set<String> getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "CreateEventRequest{" +
                "name='" + name + '\'' +
                ", eventCreator='" + eventCreator + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", category=" + category +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String name;
        private String eventCreator;
        private String address;
        private String description;
        //the request will come in as a string
        private String dateTime;
        private Set<String> category;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEventCreator(String eventCreator) {
            this.eventCreator = eventCreator;
            return this;
        }

        public Builder withAddress (String address) {
            this.address = address;
            return this;
        }

        public Builder withDescription (String description) {
            this.description = description;
            return this;
        }

        public Builder withDateTime (String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder withCategory(Set<String> category) {
            this.category = category;
            return this;
        }

        public CreateEventRequest build() {
            return new CreateEventRequest(name, eventCreator, address, description, dateTime, category);
        }
    }
}

