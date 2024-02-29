package edu.mdc.capstone.amplify.models;

import java.util.ArrayList;
import java.util.List;

public class Libraries {
    private List<Playlists> playlists;
    private List<Tracks> tracks;

    public Libraries() {
        this.playlists = new ArrayList<>();
        this.tracks = new ArrayList<>();
    }

    // Method to add a playlist to the library
    public void addPlaylistToLibrary(Playlists playlist) {
        playlists.add(playlist);
        System.out.println("Playlist '" + playlist.getPlaylistName() + "' added to the library.");
    }

    // Method to add a track to the library
    public void addTrackToLibrary(Tracks track) {
        tracks.add(track);
        System.out.println("Track '" + track.getTitle() + "' added to the library.");
    }

    // Method to display all playlists in the library
    public void displayAllPlaylists() {
        System.out.println("All Playlists in the Library:");
        for (Playlists playlist : playlists) {
            System.out.println("- " + playlist.getPlaylistName());
        }
    }

    // Method to display all tracks in the library
    public void displayAllTracks() {
        System.out.println("All Tracks in the Library:");
        for (Tracks track : tracks) {
            System.out.println("- " + track.getTitle() + " by " + track.getArtist());
        }
    }

    public static void main(String[] args) {
        // Creating an instance of the Libraries class
        Libraries myLibrary = new Libraries();

        // Creating a playlist
        Playlists playlist1 = new Playlists("Chill Mix");

        // Adding playlist to the library
        myLibrary.addPlaylistToLibrary(playlist1);

        // Creating a track
        Tracks track1 = new Tracks("Track 1", "Artist 1");

        // Adding track to the library
        myLibrary.addTrackToLibrary(track1);

        // Displaying all playlists and tracks in the library
        myLibrary.displayAllPlaylists();
        myLibrary.displayAllTracks();
    }
}
