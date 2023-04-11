package com.nashss.se.musicplaylistservice.converters;

import com.nashss.se.musicplaylistservice.dynamodb.models.AlbumTrack;
import com.nashss.se.musicplaylistservice.dynamodb.models.Playlist;
import com.nashss.se.musicplaylistservice.dynamodb.models.Profile;
import com.nashss.se.musicplaylistservice.models.PlaylistModel;
import com.nashss.se.musicplaylistservice.models.ProfileModel;
import com.nashss.se.musicplaylistservice.models.SongModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    /**
     * Converts a provided {@link Playlist} into a {@link PlaylistModel} representation.
     *
     * @param playlist the playlist to convert
     * @return the converted playlist
     */
    public PlaylistModel toPlaylistModel(Playlist playlist) {
        List<String> tags = null;
        if (playlist.getTags() != null) {
            tags = new ArrayList<>(playlist.getTags());
        }

        return PlaylistModel.builder()
                .withId(playlist.getId())
                .withName(playlist.getName())
                .withCustomerId(playlist.getCustomerId())
                .withCustomerName(playlist.getCustomerName())
                .withSongCount(playlist.getSongCount())
                .withTags(tags)
                .build();
    }

    /**
     * Converts a provided AlbumTrack into a SongModel representation.
     *
     * @param albumTrack the AlbumTrack to convert to SongModel
     * @return the converted SongModel with fields mapped from albumTrack
     */
    public SongModel toSongModel(AlbumTrack albumTrack) {
        return SongModel.builder()
                .withAsin(albumTrack.getAsin())
                .withTrackNumber(albumTrack.getTrackNumber())
                .withAlbum(albumTrack.getAlbumName())
                .withTitle(albumTrack.getSongTitle())
                .build();
    }

    public ProfileModel toProfileModel(Profile profile) {
        return ProfileModel.builder()
                .withProfileId(profile.getId())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName())
                .withLocation(profile.getLocation())
                .withGender(profile.getGender())
                .withDateOfBirth(profile.getDateOfBirth())
                .withEvents(profile.getEvents())
                .withFollowing(profile.getFollowing())
                .build();
    }



    /**
     * Converts a list of AlbumTracks to a list of SongModels.
     *
     * @param albumTracks The AlbumTracks to convert to SongModels
     * @return The converted list of SongModels
     */
    public List<SongModel> toSongModelList(List<AlbumTrack> albumTracks) {
        List<SongModel> songModels = new ArrayList<>();

        for (AlbumTrack albumTrack : albumTracks) {
            songModels.add(toSongModel(albumTrack));
        }

        return songModels;
    }

    /**
     * Converts a list of Playlists to a list of PlaylistModels.
     *
     * @param playlists The Playlists to convert to PlaylistModels
     * @return The converted list of PlaylistModels
     */
    public List<PlaylistModel> toPlaylistModelList(List<Playlist> playlists) {
        List<PlaylistModel> playlistModels = new ArrayList<>();

        for (Playlist playlist : playlists) {
            playlistModels.add(toPlaylistModel(playlist));
        }

        return playlistModels;
    }

    public List<ProfileModel> toProfileModelList(List<Profile> profiles) {
        List<ProfileModel> profileModels = new ArrayList<>();

        for (Profile p : profiles) {
            profileModels.add(toProfileModel(p));
        }

        return profileModels;
    }

}
