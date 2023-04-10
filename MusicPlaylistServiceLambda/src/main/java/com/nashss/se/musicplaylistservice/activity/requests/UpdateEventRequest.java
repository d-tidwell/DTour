package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.ZonedDateTime;
import java.util.Set;

@JsonDeserialize(builder = UpdateEventRequest.Builder.class)
public class UpdateEventRequest {
    private final String eventId;
    private final String name;
    private final String eventCreator;
    private final String address;
    private final String description;
    private final ZonedDateTime dateTime;
    private final Set<String> category;
    private final Set<String> attendees;

    private UpdateEventRequest(String eventId, String name, String eventCreator, String address, String description, ZonedDateTime dateTime, Set<String> category,Set<String> attendees) {
        this.eventId = eventId;
        this.name = name;
        this.eventCreator = eventCreator;
        this.address = address;
        this.description = description;
        this.dateTime = dateTime;
        this.category = category;
        this.attendees = attendees;
    }

    public String getEventId() {
        return eventId;
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

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public Set<String> getCategory() {
        return category;
    }

    public Set<String> getAttendees() {
        return attendees;
    }

    @Override
    public String toString() {
        return "UpdateEventRequest{" +
                "eventId='" + eventId + '\'' +
                ", name='" + name + '\'' +
                ", eventCreator='" + eventCreator + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", category='" + category + '\'' +
                ", attendees='" + attendees + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String eventId;
        private String name;
        private String eventCreator;
        private String address;
        private String description;
        private ZonedDateTime dateTime;
        private Set<String> category;
        private Set<String> attendees;

        public Builder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEventCreator(String eventCreator) {
            this.eventCreator = eventCreator;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withDateTime(ZonedDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder withCategory(Set<String> category) {
            this.category = category;
            return this;
        }

        public Builder withAttendees(Set<String> attendees) {
            this.attendees = attendees;
            return this;
        }

        public UpdateEventRequest build() {
            return new UpdateEventRequest(eventId, name, eventCreator, address, description, dateTime, category, attendees);
        }
    }
}
