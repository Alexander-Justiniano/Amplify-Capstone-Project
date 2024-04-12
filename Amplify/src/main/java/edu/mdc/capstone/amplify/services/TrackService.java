package edu.mdc.capstone.amplify.services;

import edu.mdc.capstone.amplify.models.Tracks;
import edu.mdc.capstone.amplify.models.Tracks.Genre;
import edu.mdc.capstone.amplify.repositories.TracksRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class TrackService {

    @Autowired
    private TracksRepository trackRepo;

    // Create new track
    public Tracks createTrack(Tracks newTrack, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        return trackRepo.save(newTrack);
    }

    // Update track name
    public Tracks updateTrack(Long trackId, Tracks updatedTrack, BindingResult result) {
        Optional<Tracks> existingTrackOpt = trackRepo.findById(trackId);
        if (!existingTrackOpt.isPresent()) {
            result.rejectValue("id", "NotFound", "No track found with the given ID.");
            return null;
        }

        if (result.hasErrors()) {
            return null;
        }

        Tracks existingTrack = existingTrackOpt.get();
        existingTrack.setTitle(updatedTrack.getTitle());

        return trackRepo.save(existingTrack);
    }

    // Delete track
    public void deleteTrack(Long trackId) {
        trackRepo.deleteById(trackId);
    }
    
    // Update track genre
    public Tracks updateTrackGenre(Long trackId, Genre genre) {
        Optional<Tracks> trackOpt = trackRepo.findById(trackId);
        if (trackOpt.isPresent()) {
            Tracks track = trackOpt.get();
            track.setGenre(genre);
            return trackRepo.save(track);
        } else {
            throw new EntityNotFoundException("Track with id " + trackId + " not found.");
        }
    }
    
}
