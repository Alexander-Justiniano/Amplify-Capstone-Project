package edu.mdc.capstone.amplify.models;

import java.util.ArrayList;
import java.util.List;

public class Artists {
    private String artistName;
    private List<Albums> albums;

    public Artists(String artistName) {
        this.artistName = artistName;
        this.albums = new ArrayList<>();
    }

    // Method to add an album to the artist
    public void addAlbum(Albums album) {
        albums.add(album);
    }

    // Method to get details of the artist
    public void displayArtistDetails() {
        System.out.println("Artist Name: " + artistName);
        System.out.println("Albums by the Artist:");
        for (Albums album : albums) {
            album.displayAlbumDetails();
        }
    }
}
