package com.nashss.se.musicplaylistservice.metrics;

/**
 * Constant values for use with metrics.
 */
public class MetricsConstants {
    public static final String GETEVENT_PLAYLISTNOTFOUND_COUNT = "GetEvent.EventNotFoundException.Count";
    public static final String UPDATEEVENT_INVALIDATTRIBUTEVALUE_COUNT =
        "UpdateEvent.InvalidAttributeValueException.Count";
    public static final String UPDATEEVENT_INVALIDATTRIBUTECHANGE_COUNT =
        "UpdateEvent.InvalidAttributeChangeException.Count";
    public static final String SERVICE = "Service";
    public static final String SERVICE_NAME = "MusicPlaylistService";
    public static final String NAMESPACE_NAME = "U3/MusicPlaylistService";
}
