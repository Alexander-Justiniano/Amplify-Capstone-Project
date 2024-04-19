package edu.mdc.capstone.amplify.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tracks")
public class Tracks {
    
    public enum Genre {
        Electronic, Classical, Country, Rap, Rock
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Track title is required")
    private String title;
    
    @NotNull(message = "Track length is required")
    @Min(value = 30, message = "Track must be longer than 30 seconds")
    @Max(value = 900, message = "Track must be shorter than 15 minutes")
    private Integer durationInSeconds;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Albums album;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artists artist;
    
    @NotNull(message = "Genre is required")
    @Enumerated(EnumType.STRING) // Stores enum as string
    private Genre genre;
    
    @Lob
    private byte[] audioData;
    
    @ManyToMany(mappedBy = "playlist")
    private Set<Playlists> playlist = new HashSet<>();
    
    public Tracks() {}

    public byte[] getAudioData() {
        return audioData;
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(Integer durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	public Albums getAlbum() {
		return album;
	}

	public void setAlbum(Albums album) {
		this.album = album;
	}

	public Set<Playlists> getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Set<Playlists> playlist) {
		this.playlist = playlist;
	}

	public Artists getArtist() {
		return artist;
	}

	public void setArtist(Artists artist) {
		this.artist = artist;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Set<Playlists> getPlaylists() {
		return playlist;
	}

	public void setPlaylists(Set<Playlists> playlists) {
		this.playlist = playlists;
	}
    
}
    
    