package edu.mdc.capstone.amplify;

import edu.mdc.capstone.amplify.models.Albums;
import edu.mdc.capstone.amplify.models.Tracks;
import edu.mdc.capstone.amplify.models.Tracks.Genre;
import edu.mdc.capstone.amplify.repositories.AlbumsRepository;
import edu.mdc.capstone.amplify.repositories.TracksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TracksRepositoryTest {

	@Autowired
    private TracksRepository tracksRepository;

    @Autowired
    private AlbumsRepository albumsRepository; 
    
    @Test
    public void testCreateTrackWithValidAlbum() {
        // Create and save an Album
        Albums album = new Albums();
        album.setTitle("The Charter 3");
        album.setReleaseYear(Year.of(2006)); // Corrected to use Year.of
        album.setLengthInSeconds(350);
        album = albumsRepository.save(album);

        Tracks track = new Tracks();
        track.setTitle("Hot soup");
        track.setAlbum(album); 
        track.setGenre(Genre.Rock);
        track.setDurationInSeconds(200);         
        tracksRepository.save(track); 

        Tracks track2 = new Tracks();
        track2.setTitle("Cold soup");
        track2.setAlbum(album);
        track2.setGenre(Genre.Rock);
        track2.setDurationInSeconds(200);
        tracksRepository.save(track2);

        // Fetch tracks by album ID using the ID from the saved album
        List<Tracks> tracksByAlbumId = tracksRepository.findByAlbum_Id(album.getId()); // Use album.getId() instead of a hardcoded value

        // Assert that the correct number of tracks are fetched
        assertEquals(2, tracksByAlbumId.size(), "Number of tracks should match");

        // Assert that the fetched tracks belong to the specified album
        for (Tracks fetchedTrack : tracksByAlbumId) {
            assertEquals(album.getId(), fetchedTrack.getAlbum().getId(), "Track should belong to specified album");
        }
    }
}
