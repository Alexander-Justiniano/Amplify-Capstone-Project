package edu.mdc.capstone.amplify.models;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TracksTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

	@Test
    void properArtistAndTrack_ValidationSucceeds() {
        Artists artist = new Artists("Limp Bizkit", Artists.Genre.Rock);
        Albums album = new Albums();
        album.setTitle("StarfishStarfish");
        album.setReleaseYear(java.time.Year.of(2022));
        album.setUploadDate("2023-01-01");
        album.setArtist(artist);
        album.setLengthInSeconds(3600);

        Tracks track = new Tracks();
        track.setTitle("Chocolate");
        track.setDurationInSeconds(180);
        track.setAlbum(album);
        track.setArtist(artist);
        track.setGenre(Tracks.Genre.Rock);

        Set<ConstraintViolation<Tracks>> violations = validator.validate(track);

        assertThat(violations).isEmpty();
    }

	@Test
    void blankTrackTitle_ValidationFails() {
        Artists artist = new Artists("Limp Bizkit", Artists.Genre.Rock);
        Albums album = new Albums();
        album.setTitle("StarfishStarfish");
        album.setReleaseYear(java.time.Year.of(2022));
        album.setUploadDate("2023-01-01");
        album.setArtist(artist);
        album.setLengthInSeconds(3600);

        Tracks track = new Tracks();
        track.setTitle("");
        track.setDurationInSeconds(180);
        track.setAlbum(album);
        track.setArtist(artist);
        track.setGenre(Tracks.Genre.Rock);

        Set<ConstraintViolation<Tracks>> violations = validator.validate(track);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Track title is required"));
    }
	
	@Test
    void nullTrackLength_ValidationFails() {
        Artists artist = new Artists("Limp Bizkit", Artists.Genre.Rock);
        Albums album = new Albums();
        album.setTitle("StarfishStarfish");
        album.setReleaseYear(java.time.Year.of(2022));
        album.setUploadDate("2023-01-01");
        album.setArtist(artist);
        album.setLengthInSeconds(3600);
        
        Tracks track = new Tracks();
        track.setTitle("Chocolate");
        track.setDurationInSeconds(null);
        track.setAlbum(album);
        track.setArtist(artist);
        track.setGenre(Tracks.Genre.Rock);

        Set<ConstraintViolation<Tracks>> violations = validator.validate(track);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Track length is required"));
    }

	@Test
    void shortTrackLength_ValidationFails() {
        Artists artist = new Artists("Limp Bizkit", Artists.Genre.Rock);
        Albums album = new Albums();
        album.setTitle("StarfishStarfish");
        album.setReleaseYear(java.time.Year.of(2022));
        album.setUploadDate("2023-01-01");
        album.setArtist(artist);
        album.setLengthInSeconds(3600);

        Tracks track = new Tracks();
        track.setTitle("Chocolate");
        track.setDurationInSeconds(29);
        track.setAlbum(album);
        track.setArtist(artist);
        track.setGenre(Tracks.Genre.Rock);

        Set<ConstraintViolation<Tracks>> violations = validator.validate(track);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Track must be longer than 30 seconds"));
    }
	
	@Test
    void longTrackLength_ValidationFails() {
        Artists artist = new Artists("Limp Bizkit", Artists.Genre.Rock);
        Albums album = new Albums();
        album.setTitle("StarfishStarfish");
        album.setReleaseYear(java.time.Year.of(2022));
        album.setUploadDate("2023-01-01");
        album.setArtist(artist);
        album.setLengthInSeconds(3600);

        Tracks track = new Tracks();
        track.setTitle("Chocolate");
        track.setDurationInSeconds(901);
        track.setAlbum(album);
        track.setArtist(artist);
        track.setGenre(Tracks.Genre.Rock);

        Set<ConstraintViolation<Tracks>> violations = validator.validate(track);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Track must be shorter than 15 minutes"));
    }
	
	//test genre : should be set by artist
}
