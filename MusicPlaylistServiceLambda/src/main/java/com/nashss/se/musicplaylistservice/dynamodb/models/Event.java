package com.nashss.se.musicplaylistservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an event in the events table.
 */
@DynamoDBTable(tableName = "events")
public class Event {
    private String eventId;
    private String name;
    private String eventCreator;
    private String address;
    private String description;
    private ZonedDateTime dateTime;
    private Set<String> category = new HashSet<>();
    private Set<String> attendees = new HashSet<>();


    @DynamoDBHashKey(attributeName = "eventId")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "event_creator")
    public String getCreatedBy() {
        return eventCreator;
    }

    public void setEventCreator(String eventCreator) {
        this.eventCreator = eventCreator;
    }

    @DynamoDBAttribute(attributeName = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDesciption() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "date_time")
    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @DynamoDBAttribute(attributeName = "category")
    public Set<String> getCategory() {
        return category;
    }

    public void setCategory(Set<String> category) {
        this.category.addAll(category);
    }

    @DynamoDBAttribute(attributeName = "attendees")
    public Set<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<String> attendees) {
        this.attendees.addAll(attendees);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event that = (Event) o;
        return eventId.equals(that.eventId) &&
                name.equals(that.name) &&
                address.equals(that.address) &&
                Objects.equals(eventCreator, that.eventCreator) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(category, that.category) &&
                Objects.equals(attendees, that.attendees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, name, eventCreator, address, description, dateTime, category, attendees);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", name='" + name + '\'' +
                ", eventCreator='" + eventCreator + '\'' +
                ", address=" + address +
                ", description='" + description + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", category='" + category + '\'' +
                ", attendees='" + attendees + '\'' +
                '}';
    }
}
