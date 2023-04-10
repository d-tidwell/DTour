package com.nashss.se.musicplaylistservice.models;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EventModel {
    private final String eventId;
    private final String name;
    private final String eventCreator;
    private final String eventAddress;
    private final String description;
    private final ZonedDateTime dateTime;
    private Set<String> category;
    private Set<String> attendees;

    private EventModel(String eventId, String name, String eventCreator, String eventAddress, String description,
                          ZonedDateTime dateTime, Set<String> category, Set<String> attendees) {
        this.eventId = eventId;
        this.name = name;
        this.eventCreator = eventCreator;
        this.eventAddress = eventAddress;
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

    public String getEventAddress() {
        return eventAddress;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventModel that = (EventModel) o;

        return  Objects.equals(eventId, that.eventId) && Objects.equals(name, that.name) &&
                Objects.equals(eventCreator, that.eventCreator) &&
                Objects.equals(eventAddress, that.eventAddress) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(category, that.category) &&
                Objects.equals(attendees, that.attendees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, name, eventCreator, eventAddress, description, dateTime, category, attendees);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String eventId;
        private String name;
        private String eventCreator;
        private String eventAddress;
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

        public Builder withEventAddress(String eventAddress) {
            this.eventAddress = eventAddress;
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
            this.category = new HashSet<>();
            category.addAll(category);
            return this;
        }

        public Builder withAttendees(Set<String> newAttendees) {
            this.attendees = new HashSet<>();
            attendees.addAll(newAttendees);
            return this;
        }

        public EventModel build() {
            return new EventModel(eventId, name, eventCreator, eventAddress, description, dateTime, category, attendees);
        }
    }
}
