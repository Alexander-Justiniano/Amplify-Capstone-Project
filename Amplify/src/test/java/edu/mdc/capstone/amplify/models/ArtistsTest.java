package edu.mdc.capstone.amplify.models;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Set;

class ArtistsTest {

	private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    void properArtist_ValidationSucceeds() {
        Artists artist = new Artists();
        artist.setName("AwesomeDude");
        artist.setGenre(Artists.Genre.Rock);

        Set<ConstraintViolation<Artists>> violations = validator.validate(artist);

        assertThat(violations).isEmpty();
    }
    
    @Test
    void shortArtistName_ValidationFails() {
        Artists artist = new Artists();
        artist.setName("A");
        artist.setGenre(Artists.Genre.Rock);

        Set<ConstraintViolation<Artists>> violations = validator.validate(artist);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void LongArtistName_ValidationFails() {
        Artists artist = new Artists();
        artist.setName("AwesoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooomeDudeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        artist.setGenre(Artists.Genre.Rap);

        Set<ConstraintViolation<Artists>> violations = validator.validate(artist);

        assertThat(violations).isNotEmpty();
    }
    
    @Test
    void blankArtistName_ValidationFails() {
        Artists artist = new Artists();
        artist.setName("");
        artist.setGenre(Artists.Genre.Rap);

        Set<ConstraintViolation<Artists>> violations = validator.validate(artist);

        assertThat(violations).isNotEmpty();
    }
    
    @Test
    void blankGenre_ValidationFails() {
        Artists artist = new Artists();
        artist.setName("AwesomeDude");
        artist.setGenre(null);

        Set<ConstraintViolation<Artists>> violations = validator.validate(artist);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Genre is required"));
    }
    
}
