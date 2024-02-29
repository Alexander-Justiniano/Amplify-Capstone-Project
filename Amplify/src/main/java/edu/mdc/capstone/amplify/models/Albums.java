package edu.mdc.capstone.amplify.models;

import java.util.ArrayList;
import java.util.List;

public class Albums {
    private String albumTitle;
    private List<Song> songs;

    public Albums(String albumTitle) {
        this.albumTitle = albumTitle;
        this.songs = new ArrayList<>();
    }

    // Method to add a song to the album
    public void addSong(Song song) {
        songs.add(song);
    }

    // Method to get details of the album
    public void displayAlbumDetails() {
        System.out.println("Album Title: " + albumTitle);
        System.out.println("Songs in the Album:");
        for (Song song : songs) {
            System.out.println("- " + song.getTitle());
        }
    }
}
