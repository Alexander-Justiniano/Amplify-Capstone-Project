package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.Tracks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TracksRepository extends CrudRepository<Tracks, Long> {
   
	// Find all tracks in an album
    List<Tracks> findByAlbum_Id(Long albumId);
    
    // Find track by name
    Optional<Tracks> findByTitle(String title);
    
}
