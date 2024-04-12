package edu.mdc.capstone.amplify;


import edu.mdc.capstone.amplify.models.User;
import edu.mdc.capstone.amplify.models.Playlists;
import edu.mdc.capstone.amplify.repositories.UserRepository;
import edu.mdc.capstone.amplify.repositories.PlaylistsRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

@SpringBootTest
@Transactional
public class RelationshipTests {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PlaylistsRepository playlistRepository;

    @Test
    public void testUserPlaylistRelationship() {
        User user = new User();
        user.setUserName("kenny");
        user.setEmail("kenny@amplify.com"); 
        user.setPassword("Por3");
        user.setConfirm("Password123");
        userRepository.save(user);

        Playlists playlist = new Playlists();
        playlist.setOwner(user);
        playlist.setName("apples");
        playlist.setCreatedDate(null);
        playlist.setDescription("fruits");
        playlistRepository.save(playlist);

  
        Optional<Playlists> retrievedPlaylist = playlistRepository.findById(playlist.getId());
        assertTrue(retrievedPlaylist.isPresent());
        assertEquals(user.getId(), retrievedPlaylist.get().getOwner().getId());
    }

	public PlaylistsRepository getPlaylistRepository() {
		return playlistRepository;
	}

	public void setPlaylistRepository(PlaylistsRepository playlistRepository) {
		this.playlistRepository = playlistRepository;
	}

}

