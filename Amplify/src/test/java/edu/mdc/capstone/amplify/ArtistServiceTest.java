package edu.mdc.capstone.amplify;

import edu.mdc.capstone.amplify.models.Artists;
import edu.mdc.capstone.amplify.repositories.ArtistsRepository;
import edu.mdc.capstone.amplify.services.ArtistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ArtistServiceTest {

    @Mock
    private ArtistsRepository artistsRepository;

    @InjectMocks
    private ArtistService artistService;

    private Artists artist;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        artist = new Artists();
        artist.setName("Artist Name");
        bindingResult = new BeanPropertyBindingResult(artist, "artist");
    }

    @Test
    void testCreateArtist_Success() {
        when(artistsRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(artistsRepository.save(any(Artists.class))).thenReturn(artist);

        Artists savedArtist = artistService.createArtist(artist, bindingResult);

        assertNotNull(savedArtist);
        verify(artistsRepository, times(1)).save(any(Artists.class));
    }

    @Test
    void testCreateArtist_Fail_DuplicateName() {
        when(artistsRepository.findByName(anyString())).thenReturn(Optional.of(artist));

        Artists savedArtist = artistService.createArtist(artist, bindingResult);

        assertNull(savedArtist);
        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("name"));
    }

    @Test
    void testFindArtistById_Found() {
        when(artistsRepository.findById(anyLong())).thenReturn(Optional.of(artist));

        Optional<Artists> foundArtist = artistService.findArtistById(1L);

        assertTrue(foundArtist.isPresent());
        assertEquals(artist.getName(), foundArtist.get().getName());
    }

    @Test
    void testFindArtistById_NotFound() {
        when(artistsRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Artists> foundArtist = artistService.findArtistById(1L);

        assertFalse(foundArtist.isPresent());
    }

    @Test
    void testUpdateArtist_Success() {
        when(artistsRepository.existsById(anyLong())).thenReturn(true);
        when(artistsRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(artistsRepository.save(any(Artists.class))).thenReturn(artist);

        artist.setId(1L);
        Artists updatedArtist = artistService.updateArtist(1L, artist, bindingResult);

        assertNotNull(updatedArtist);
        assertEquals(1L, updatedArtist.getId());
    }

    @Test
    void testUpdateArtist_Fail_NotFound() {
        when(artistsRepository.existsById(anyLong())).thenReturn(false);

        Artists updatedArtist = artistService.updateArtist(1L, artist, bindingResult);

        assertNull(updatedArtist);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testUpdateArtist_Fail_DuplicateName() {
        when(artistsRepository.existsById(anyLong())).thenReturn(true);
        when(artistsRepository.findByName(anyString())).thenReturn(Optional.of(artist));

        artist.setId(2L); // Simulate different artist with the same name
        Artists updatedArtist = artistService.updateArtist(1L, artist, bindingResult);

        assertNull(updatedArtist);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testDeleteArtist() {
        doNothing().when(artistsRepository).deleteById(anyLong());

        artistService.deleteArtist(1L);

        verify(artistsRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testFindArtistByName_Found() {
        when(artistsRepository.findByName(anyString())).thenReturn(Optional.of(artist));

        Optional<Artists> foundArtist = artistService.findArtistByName("Artist Name");

        assertTrue(foundArtist.isPresent());
        assertEquals(artist.getName(), foundArtist.get().getName());
    }
}
