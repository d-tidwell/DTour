package com.nashss.se.musicplaylistservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;
import java.util.Set;

@DynamoDBTable(tableName = "profiles")
public class Profile {

    private String id;
    private String firstName;
    private String lastName;
    private String location;
    private String gender;
    private String dateOfBirth;
    private Set<String> following;
    private Set<String> events;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBAttribute(attributeName = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @DynamoDBAttribute(attributeName = "dateOfBirth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @DynamoDBAttribute(attributeName = "following")
    public Set<String> getFollowing() {
        return following;
    }

    public void setFollowing(Set<String> following) {
        this.following = following;
    }

    @DynamoDBAttribute(attributeName = "events")
    public Set<String> getEvents() {
        return events;
    }

    public void setEvents(Set<String> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id.equals(profile.id) && firstName.equals(profile.firstName) && lastName.equals(profile.lastName) && location.equals(profile.location) && gender.equals(profile.gender) && dateOfBirth.equals(profile.dateOfBirth) && Objects.equals(following, profile.following) && Objects.equals(events, profile.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, location, gender, dateOfBirth, following, events);
    }
}
