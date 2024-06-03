package edu.mdc.capstone.amplify.models;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

class AlbumsTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	    
	@Test
	void properAlbum_ValidationSucceeds() {
		Artists artist = new Artists("AwesomeDude", Artists.Genre.Rock);
		Albums album = new Albums();
		album.setTitle("Ravioli");
		album.setDescription("valid description");
	    album.setReleaseYear(Year.of(2022));
	    album.setUploadDate("2023-01-01");
	    album.setArtist(artist);
	    album.setLengthInSeconds(1245);

	    Set<ConstraintViolation<Albums>> violations = validator.validate(album);

	    assertThat(violations).isEmpty();
	}
	    
	@Test
	void blankAlbumTitle_ValidationFails() {
		Artists artist = new Artists("AwesomeDude", Artists.Genre.Rock);
	    Albums album = new Albums();
	    album.setTitle("");
	    album.setDescription("Valid  description.");
	    album.setReleaseYear(Year.of(2022));
	    album.setUploadDate("2023-01-01");
	    album.setArtist(artist);
	    album.setLengthInSeconds(1245);

	    Set<ConstraintViolation<Albums>> violations = validator.validate(album);

	    assertThat(violations).isNotEmpty();
	    assertThat(violations).anyMatch(v -> v.getMessage().equals("Album title is required"));
	}
	    
	@Test
    void longAlbumTitle_ValidationFails() {
        Artists artist = new Artists("AwesomeDude", Artists.Genre.Rock);
        Albums album = new Albums();
        album.setTitle("looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong Album Title.");
        album.setDescription("Valid description");
        album.setReleaseYear(Year.of(2022));
        album.setUploadDate("2023-01-01");
        album.setArtist(artist);
        album.setLengthInSeconds(1245);

        Set<ConstraintViolation<Albums>> violations = validator.validate(album);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Title must be between 1 and 80 characters"));
    }
	
	@Test
    void tooLongAlbumDescription_ValidationFails() {
        Artists artist = new Artists("Valid Artist Name", Artists.Genre.Rock);
        Albums album = new Albums();
        album.setTitle("Lemons");
        album.setDescription("looooooooooooooooooooooooooooooooooooooooooooooooooong Descriptiooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooon ".repeat(300));
        album.setReleaseYear(Year.of(2022));
        album.setUploadDate("2023-01-01");
        album.setArtist(artist);
        album.setLengthInSeconds(3600);

        Set<ConstraintViolation<Albums>> violations = validator.validate(album);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Description must be less than 1000 characters"));
    }

	@Test
	void nullReleaseYear_ValidationFails() {
		Artists artist = new Artists("AwesomeDude", Artists.Genre.Rock);
		Albums album = new Albums();
		album.setTitle("Pastromi");
		album.setDescription("Valid description");
		album.setReleaseYear(null);
		album.setUploadDate("2023-01-01");
		album.setArtist(artist);
		album.setLengthInSeconds(3600);

		Set<ConstraintViolation<Albums>> violations = validator.validate(album);

		assertThat(violations).isNotEmpty();
		assertThat(violations).anyMatch(v -> v.getMessage().equals("Release year is required"));
	}

	  
	@Test
	void blankUploadDate_ValidationFails() {
		Artists artist = new Artists("AwesomeDude", Artists.Genre.Rock);
        Albums album = new Albums();
        album.setTitle("OrangeApples");
        album.setDescription("Valid description");
        album.setReleaseYear(Year.of(2022));
        album.setUploadDate("");
        album.setArtist(artist);
        album.setLengthInSeconds(3600);

        Set<ConstraintViolation<Albums>> violations = validator.validate(album);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Upload date is required"));
    }	
	
	@Test
	void nullAlbumLength_ValidationFails() {
		Artists artist = new Artists("AwesomeDude", Artists.Genre.Rock);
		Albums album = new Albums();
		album.setTitle("Peaches");
		album.setDescription("Valid description");
		album.setReleaseYear(Year.of(2022));
		album.setUploadDate("2023-01-01");
		album.setArtist(artist);
		album.setLengthInSeconds(null);

		Set<ConstraintViolation<Albums>> violations = validator.validate(album);
		
		assertThat(violations).isNotEmpty();
		assertThat(violations).anyMatch(v -> v.getMessage().equals("Album length is required"));
	}

	@Test
	void shortAlbumLength_ValidationFails() {
		Artists artist = new Artists("AwesomeDude", Artists.Genre.Rock);
		Albums album = new Albums();
		album.setTitle("Grapes");
		album.setDescription("Valid description");
        album.setReleaseYear(Year.of(2022));
	    album.setUploadDate("2023-01-01");
	    album.setArtist(artist);
	    album.setLengthInSeconds(49);

	    Set<ConstraintViolation<Albums>> violations = validator.validate(album);

	    assertThat(violations).isNotEmpty();
	    assertThat(violations).anyMatch(v -> v.getMessage().equals("Album must be longer than 1 minute"));
	}


	@Test
    void longAlbumLength_ValidationFails() {
		Artists artist = new Artists("AwesomeDude", Artists.Genre.Rock);
		Albums album = new Albums();
		album.setTitle("Kiwis");
		album.setDescription("Valid description");
		album.setReleaseYear(Year.of(2022));
		album.setUploadDate("2023-01-01");
		album.setArtist(artist);
		album.setLengthInSeconds(9999999);

		Set<ConstraintViolation<Albums>> violations = validator.validate(album);

		assertThat(violations).isNotEmpty();
		assertThat(violations).anyMatch(v -> v.getMessage().equals("Album must be shorter than 2 hours"));
	}
	
}
