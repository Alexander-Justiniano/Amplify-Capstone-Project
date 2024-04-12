package edu.mdc.capstone.amplify.models;

import edu.mdc.capstone.amplify.models.Tracks;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class Listening_History {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", nullable = false)
    private Tracks tracks;

    
    @NotNull
    @Column(nullable = false)
    private LocalDateTime listenedAt;

    public Listening_History() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tracks getTrack() {
		return tracks;
	}

	public void setTrack(Tracks tracks) {
		this.tracks = tracks;
	}

	public LocalDateTime getListenedAt() {
		return listenedAt;
	}

	public void setListenedAt(LocalDateTime listenedAt) {
		this.listenedAt = listenedAt;
	}


	
    
	}


