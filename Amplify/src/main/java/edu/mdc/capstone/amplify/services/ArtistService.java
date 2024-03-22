package edu.mdc.capstone.amplify.services;

import edu.mdc.capstone.amplify.models.Artists;
import edu.mdc.capstone.amplify.repositories.ArtistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistsRepository artistsRepo;

    public Artists createArtist(Artists newArtist, BindingResult result) {
    	
    	// Check if artist name already exists
        Optional<Artists> existingArtist = artistsRepo.findByName(newArtist.getName());
        
        // Reject if not unique
        if (existingArtist.isPresent()) {
            result.rejectValue("name", "Duplicate", "An artist with this name already exists!");
            return null;
        }

        if (result.hasErrors()) {
            return null;
        }
        // Save and return new artist
        return artistsRepo.save(newArtist);
    }

    public Optional<Artists> findArtistById(Long id) {
        return artistsRepo.findById(id);
    }

    public Iterable<Artists> findAllArtists() {
        return artistsRepo.findAll();
    }
    
    // Update artists name
    public Artists updateArtist(Long id, Artists updatedArtist, BindingResult result) {
        if (!artistsRepo.existsById(id)) {
            result.rejectValue("id", "NotFound", "No artist found with the given ID.");
            return null;
        }

        // Check if updated name already exists
        Optional<Artists> existingArtist = artistsRepo.findByName(updatedArtist.getName());
        if (existingArtist.isPresent() && !existingArtist.get().getId().equals(id)) {
            result.rejectValue("name", "Duplicate", "An artist with this name already exists!");
            return null;
        }

        if (result.hasErrors()) {
            return null;
        }

        updatedArtist.setId(id); 
        return artistsRepo.save(updatedArtist);
    }

    // Delete artist
    public void deleteArtist(Long id) {
        artistsRepo.deleteById(id);
    }
    
    // Find artist by name
    public Optional<Artists> findArtistByName(String name) {
        return artistsRepo.findByName(name);
    }
}
