package edu.mdc.capstone.amplify;

import edu.mdc.capstone.amplify.models.Albums;
import edu.mdc.capstone.amplify.repositories.AlbumsRepository;
import edu.mdc.capstone.amplify.services.AlbumService;
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

class AlbumServiceTest {

    @Mock
    private AlbumsRepository albumsRepository;

    @InjectMocks
    private AlbumService albumService;

    private Albums album;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        album = new Albums();
        album.setTitle("Album Title");
        bindingResult = new BeanPropertyBindingResult(album, "album");
    }

    @Test
    void testCreateAlbum_Success() {
        when(albumsRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(albumsRepository.save(any(Albums.class))).thenReturn(album);

        Albums savedAlbum = albumService.createAlbum(album, bindingResult);

        assertNotNull(savedAlbum);
        verify(albumsRepository, times(1)).save(any(Albums.class));
    }

    @Test
    void testCreateAlbum_Fail_DuplicateTitle() {
        when(albumsRepository.findByTitle(anyString())).thenReturn(Optional.of(album));

        Albums savedAlbum = albumService.createAlbum(album, bindingResult);

        assertNull(savedAlbum);
        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("title"));
    }

    @Test
    void testUpdateAlbum_Success() {
        when(albumsRepository.existsById(anyLong())).thenReturn(true);
        when(albumsRepository.save(any(Albums.class))).thenReturn(album);

        album.setId(1L);
        Albums updatedAlbum = albumService.updateAlbum(1L, album, bindingResult);

        assertNotNull(updatedAlbum);
        assertEquals(1L, updatedAlbum.getId());
    }

    @Test
    void testUpdateAlbum_Fail_NotFound() {
        when(albumsRepository.existsById(anyLong())).thenReturn(false);

        Albums updatedAlbum = albumService.updateAlbum(1L, album, bindingResult);

        assertNull(updatedAlbum);
        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("id"));
    }

    @Test
    void testDeleteAlbum() {
        doNothing().when(albumsRepository).deleteById(anyLong());

        albumService.deleteAlbum(1L);

        verify(albumsRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testFindAlbumById_Found() {
        when(albumsRepository.findById(anyLong())).thenReturn(Optional.of(album));

        Optional<Albums> foundAlbum = albumService.findAlbumById(1L);

        assertTrue(foundAlbum.isPresent());
        assertEquals(album.getTitle(), foundAlbum.get().getTitle());
    }

    @Test
    void testFindAlbumById_NotFound() {
        when(albumsRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Albums> foundAlbum = albumService.findAlbumById(1L);

        assertFalse(foundAlbum.isPresent());
    }

    @Test
    void testFindAlbumByTitle_Found() {
        when(albumsRepository.findByTitle(anyString())).thenReturn(Optional.of(album));

        Optional<Albums> foundAlbum = albumService.findAlbumByTitle("Album Title");

        assertTrue(foundAlbum.isPresent());
        assertEquals(album.getTitle(), foundAlbum.get().getTitle());
    }
}
