package com.nashss.se.musicplaylistservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;
import java.util.Set;

@DynamoDBTable(tableName = "Profile")
public class Profile {

    private String UserId;
    private Set<String> following;
    private Set<String> events;

    @DynamoDBHashKey(attributeName = "id")
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
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
        return UserId.equals(profile.UserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserId);
    }
}
