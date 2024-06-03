package edu.mdc.capstone.amplify.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "artists")
public class Artists {
	
	public enum Genre {
		Electronic, Classical, Country, Rap, Rock
    }
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Artist name required")
    @Size(min = 2, max = 80, message = "Artist name must be between 2 and 80 characters")
    private String name;
    
    @NotNull(message = "Genre is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    public Artists() {}
    
    public Artists(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
   
}