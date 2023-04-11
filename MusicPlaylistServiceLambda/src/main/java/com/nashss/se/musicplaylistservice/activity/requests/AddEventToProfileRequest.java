package com.nashss.se.musicplaylistservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nashss.se.musicplaylistservice.activity.results.AddSongToPlaylistResult;

import java.time.ZonedDateTime;
import java.util.Set;

@JsonDeserialize(builder = AddEventToProfileRequest.Builder.class)
public class AddEventToProfileRequest {
    private final String eventId;
    private final String name;
    private final String address;
    private final String eventCreator;
    private final String description;
    private final ZonedDateTime dateTime;
    private Set<String> category;
    private Set<String> attendees;
    private String profileId;

    private AddEventToProfileRequest(String eventId, String name, String address, String eventCreator,
                                     String description, ZonedDateTime dateTime, Set<String> category, Set<String> attendees, String profileId) {
        this.eventId = eventId;
        this.name = name;
        this.address = address;
        this.eventCreator = eventCreator;
        this.description = description;
        this.dateTime = dateTime;
        this.category = category;
        this.attendees = attendees;
        this.profileId = profileId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEventCreator() {
        return eventCreator;
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

    public String getProfileId() {
        return profileId;
    }

    @Override
    public String toString() {
        return "AddEventToProfileRequest{" +
                "eventId='" + eventId + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address + + '\'' +
                ", eventCreator=" + eventCreator + + '\'' +
                ", description=" + description + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", category='" + category + '\'' +
                ", attendees='" + attendees + '\'' +
                ", profileId='" + profileId + '\'' +
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
        private String address;
        private String eventCreator;
        private String description;
        private ZonedDateTime dateTime;
        private Set<String> category;
        private Set<String> attendees;
        private String profileId;



        public Builder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withEventCreator(String eventCreator) {
            this.eventCreator = eventCreator;
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

        public Builder withProfileId(String profileId) {
            this.profileId = profileId;
            return this;
        }

        public AddEventToProfileRequest build() {
            return new AddEventToProfileRequest(eventId, name, address, eventCreator, description, dateTime, category, attendees, profileId);
        }


    }
}
