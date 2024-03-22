package edu.mdc.capstone.amplify.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "playlists")
public class Playlists {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Playlist name is required")
    @Size(min = 1, max = 100, message = "Playlist name must be between 1 and 255 characters")
    private String name;

	@Size(min=3, max=30, message= "Description must be less than 250 words")
    private String description;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	@ManyToMany
	@JoinTable(
	name = "playlist_contributors",
	joinColumns = @JoinColumn(name = "playlist_id"),
	inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	private Set<User> contributors = new HashSet<>();

	@ManyToMany(mappedBy = "playlists")
	private Set<Tracks> tracks = new HashSet<>();

	@CreationTimestamp
	private LocalDate createdDate;

	public Playlists() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Set<User> getContributors() {
		return contributors;
	}

	public void setContributors(Set<User> contributors) {
		this.contributors = contributors;
	}

	public Set<Tracks> getTracks() {
		return tracks;
	}

	public void setTracks(Set<Tracks> tracks) {
		this.tracks = tracks;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

 }

