package edu.mdc.capstone.amplify.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name = "tracks")
public class Tracks {
    
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

    public Tracks() {}

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

	public Artists getArtist() {
		return artist;
	}

	public void setArtist(Artists artist) {
		this.artist = artist;
	}
    
}
    
    