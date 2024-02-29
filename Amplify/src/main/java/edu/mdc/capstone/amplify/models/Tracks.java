package edu.mdc.capstone.amplify.models;

public class Tracks {
    private String title;
    private String artist;

    public Tracks(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
