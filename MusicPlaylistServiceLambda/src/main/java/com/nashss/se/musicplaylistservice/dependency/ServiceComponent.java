package com.nashss.se.musicplaylistservice.dependency;

import com.nashss.se.musicplaylistservice.activity.*;

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

    CreateProfileActivity provideCreateProfileActivity();

    AddEventToProfileActivity provideAddEventToProfileActivity();

    RemoveFollowingFromProfileActivity provideRemoveFromFollowingActivity();

    AddFollowingToProfileActivity provideAddProfileToFollowingActivity();

    CreateEventActivity provideCreateEventActivity();

    GetProfileActivity provideGetProfileActivity();
    UpdateProfileActivity provideUpdateProfileActivity();

    GetAllEventsActivity provideGetAllEventsActivity();

    GetEventActivity provideGetEventActivity();
}
