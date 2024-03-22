package edu.mdc.capstone.amplify.services;

import edu.mdc.capstone.amplify.models.Albums;
import edu.mdc.capstone.amplify.repositories.AlbumsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumsRepository albumsRepo;

    // Create new album
    public Albums createAlbum(Albums newAlbum, BindingResult result) {
       
    	// Check if album name already exists
        Optional<Albums> existingAlbum = albumsRepo.findByTitle(newAlbum.getTitle());
        
        // Reject if album name is taken
        if (existingAlbum.isPresent()) 
        {
            result.rejectValue("title", "Duplicate", "Album name already exist, is not unique");
            return null;
        }
        
        // Check for errors
        if (result.hasErrors()) {
            return null;
        }

        // Save new album
        return albumsRepo.save(newAlbum);
    }

    // Update existing album
    public Albums updateAlbum(Long id, Albums updatedAlbum, BindingResult result) {
        
    	// Checks if album exist
        if (!albumsRepo.existsById(id)) {
            result.rejectValue("id", "NotFound", "No album found with the given ID.");
            return null;
        }
        
        // Error check
        if (result.hasErrors()) {
            return null;
        }
        
        // Save the updated album to the database
        updatedAlbum.setId(id);
        return albumsRepo.save(updatedAlbum);
    }

    // Delete album
    public void deleteAlbum(Long id) {
        albumsRepo.deleteById(id);
    }

    // Find album by ID
    public Optional<Albums> findAlbumById(Long id) {
        return albumsRepo.findById(id);
    }

    // Fine album by title
    public Optional<Albums> findAlbumByTitle(String title) {
        return albumsRepo.findByTitle(title);
    }
}

