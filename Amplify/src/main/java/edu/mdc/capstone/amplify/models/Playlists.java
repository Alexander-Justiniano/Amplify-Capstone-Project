package edu.mdc.capstone.amplify.models;

import java.util.ArrayList;
import java.util.List;

public class Playlists {
    private String playlistName;
    private List<Tracks> tracks;

    public Playlists(String playlistName) {
        this.playlistName = playlistName;
        this.tracks = new ArrayList<>();
    }

    // Method to add a track to the playlist
    public void addTrackToPlaylist(Tracks track) {
        tracks.add(track);
        System.out.println("Track '" + track.getTitle() + "' added to playlist '" + playlistName + "'.");
    }

    // Method to display playlist details
    public void displayPlaylistDetails() {
        System.out.println("Playlist: " + playlistName);
        System.out.println("Tracks in the Playlist:");
        for (Tracks track : tracks) {
            System.out.println("- " + track.getTitle() + " by " + track.getArtist());
        }
    }

    public static void main(String[] args) {
        // Creating an instance of the Playlists class
        Playlists myPlaylist = new Playlists("My Favorites");

        // Creating tracks
        Tracks track1 = new Tracks("Track 1", "Artist 1");
        Tracks track2 = new Tracks("Track 2", "Artist 2");

        // Adding tracks to the playlist
        myPlaylist.addTrackToPlaylist(track1);
        myPlaylist.addTrackToPlaylist(track2);

        // Displaying playlist details
        myPlaylist.displayPlaylistDetails();
    }
}