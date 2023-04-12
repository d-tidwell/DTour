package com.nashss.se.musicplaylistservice.dependency;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.*;
import com.nashss.se.musicplaylistservice.activity.requests.AddEventToProfileRequest;
import com.nashss.se.musicplaylistservice.activity.requests.AddProfileToFollowingRequest;
import com.nashss.se.musicplaylistservice.activity.requests.CreateEventRequest;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return UpdatePlaylistActivity
     */
     UpdateEventActivity provideUpdateEventActivity();

    UpdatePlaylistActivity provideUpdatePlaylistActivity();
    
    CreateProfileActivity provideCreateProfileActivity();

    AddEventToProfileActivity provideAddEventToProfileActivity();

    RemoveFromFollowingActivity provideRemoveFromFollowingActivity();

    AddProfileToFollowingActivity provideAddProfileToFollowingActivity();

    CreateEventActivity provideCreateEventActivity();

}
