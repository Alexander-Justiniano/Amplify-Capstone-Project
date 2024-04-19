package edu.mdc.capstone.amplify.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mdc.capstone.amplify.models.Tracks;

@Repository
public interface TracksRepository extends JpaRepository<Tracks, Long> {
   
	// Find all tracks in an album
    List<Tracks> findByAlbum_Id(Long albumId);
    
    // Find track by name
    Optional<Tracks> findByTitle(String title);
    
}
