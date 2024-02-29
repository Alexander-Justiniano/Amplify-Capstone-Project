package edu.mdc.capstone.amplify.models;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private String username;
    private List<Playlists> playlists;
    private List<Users> socialConnections;

    public Users(String username) {
        this.username = username;
        this.playlists = new ArrayList<>();
        this.socialConnections = new ArrayList<>();
    }

    // Method to create a new playlist
    public void createPlaylist(String playlistName) {
        Playlists newPlaylist = new Playlists(playlistName);
        playlists.add(newPlaylist);
        System.out.println("Playlist '" + playlistName + "' created for user '" + username + "'.");
    }

    // Method to display user playlists
    public void displayUserPlaylists() {
        System.out.println("Playlists of User '" + username + "':");
        for (Playlists playlist : playlists) {
            playlist.displayPlaylistDetails();
        }
    }

    // Method to connect with another user
    public void connectWithUser(Users otherUser) {
        socialConnections.add(otherUser);
        System.out.println("User '" + username + "' connected with user '" + otherUser.getUsername() + "'.");
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    public static void main(String[] args) {
        // Creating user instances
        Users user1 = new Users("User1");
        Users user2 = new Users("User2");

        // Creating playlists for users
        user1.createPlaylist("My Favorites");
        user2.createPlaylist("Workout Jams");

        // Displaying user playlists
        user1.displayUserPlaylists();
        user2.displayUserPlaylists();

        // Connecting users
        user1.connectWithUser(user2);
    }
}
