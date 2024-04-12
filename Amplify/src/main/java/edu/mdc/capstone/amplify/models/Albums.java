package edu.mdc.capstone.amplify.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.Year;
import java.util.Set;


@Entity
@Table(name="albums")
public class Albums {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank(message = "Album title is required")
	@Size(min = 1, max = 80, message = "Title must be between 1 and 80 characters")
	private String title;
	 
	@Size(max = 1000, message = "Description must be less than 1000 characters")
	private String description;
	 
	@NotNull(message = "Release year is required")
	private Year releaseYear; 
	 
	@NotBlank(message = "Upload date is required")
	private String uploadDate; 
	 
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "artist_id", nullable = false)
    private Artists artist;
	 
	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Tracks> tracks;
	
	@NotNull(message = "Album length is required")
    @Min(value = 60, message = "Album must be longer than 1 minute")
	@Max(value = 10800, message = "Album must be shorter than 2 hours")
	private Integer lengthInSeconds;
	  
	public Albums() {}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Year getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Year releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Artists getArtist() {
		return artist;
	}

	public void setArtist(Artists artist) {
		this.artist = artist;
	}

	public Set<Tracks> getTracks() {
		return tracks;
	}

	public void setTracks(Set<Tracks> tracks) {
		this.tracks = tracks;
	}

	public Integer getLengthInSeconds() {
		return lengthInSeconds;
	}

	public void setLengthInSeconds(Integer lengthInSeconds) {
		this.lengthInSeconds = lengthInSeconds;
	}

}