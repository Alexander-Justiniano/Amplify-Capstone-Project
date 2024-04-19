package edu.mdc.capstone.amplify;

import edu.mdc.capstone.amplify.models.Playlists;
import edu.mdc.capstone.amplify.models.Tracks;
import edu.mdc.capstone.amplify.models.User;
import edu.mdc.capstone.amplify.repositories.PlaylistsRepository;
import edu.mdc.capstone.amplify.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PlaylistServiceTest {

    @Mock
    private PlaylistsRepository playlistsRepository;

    @InjectMocks
    private PlaylistService playlistService;

    private Playlists playlist;
    private User user;
    private Tracks track;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playlist = new Playlists();
        user = new User();
        track = new Tracks();
        bindingResult = new BeanPropertyBindingResult(playlist, "playlist");
        user.setId(1L);
        playlist.setName("Playlist Name");
        playlist.setOwner(user);
        Set<Tracks> tracks = new HashSet<>();
        tracks.add(track);
        playlist.setTracks(tracks);
        track.setId(1L); // Assuming the track also has an ID
    }

    // Test cases for createPlaylist and updatePlaylist as previously provided...

    @Test
    void testDeletePlaylist_Success() {
        Long playlistId = 1L;
        when(playlistsRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        playlistService.deletePlaylist(playlistId, user, bindingResult);

        verify(playlistsRepository, times(1)).delete(playlist);
    }

    @Test
    void testDeletePlaylist_Failure_NotFound() {
        Long playlistId = 1L;
        when(playlistsRepository.findById(playlistId)).thenReturn(Optional.empty());

        playlistService.deletePlaylist(playlistId, user, bindingResult);

        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("id"));
        verify(playlistsRepository, never()).delete(any(Playlists.class));
    }

    @Test
    void testAddTrackToPlaylist_Success() {
        Long playlistId = 1L;
        when(playlistsRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        playlistService.addTrackToPlaylist(playlistId, track, user, bindingResult);

        assertTrue(playlist.getTracks().contains(track));
        verify(playlistsRepository, times(1)).save(playlist);
    }

    @Test
    void testRemoveTrackFromPlaylist_Success() {
        Long playlistId = 1L;
        when(playlistsRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        playlistService.removeTrackFromPlaylist(playlistId, track, user, bindingResult);

        assertFalse(playlist.getTracks().contains(track));
        verify(playlistsRepository, times(1)).save(playlist);
    }

    @Test
    void testAddTrackToPlaylist_Failure_PlaylistNotFound() {
        Long playlistId = 1L;
        when(playlistsRepository.findById(playlistId)).thenReturn(Optional.empty());

        playlistService.addTrackToPlaylist(playlistId, track, user, bindingResult);

        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("id"));
        verify(playlistsRepository, never()).save(any(Playlists.class));
    }

    @Test
    void testRemoveTrackFromPlaylist_Failure_PlaylistNotFound() {
        Long playlistId = 1L;
        when(playlistsRepository.findById(playlistId)).thenReturn(Optional.empty());

        playlistService.removeTrackFromPlaylist(playlistId, track, user, bindingResult);

        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("id"));
        verify(playlistsRepository, never()).save(any(Playlists.class));
    }

    @Test
    void testRemoveTrackFromPlaylist_Failure_TrackNotInPlaylist() {
        Long playlistId = 1L;
        // The track is not in the playlist
        playlist.setTracks(new HashSet<>());
        
        when(playlistsRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        playlistService.removeTrackFromPlaylist(playlistId, track, user, bindingResult);

        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("tracks"));
        verify(playlistsRepository, never()).save(playlist);
    }

}
