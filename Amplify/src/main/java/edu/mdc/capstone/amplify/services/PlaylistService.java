package edu.mdc.capstone.amplify.services;

import edu.mdc.capstone.amplify.models.Playlists;
import edu.mdc.capstone.amplify.models.User;
import edu.mdc.capstone.amplify.models.Tracks;
import edu.mdc.capstone.amplify.repositories.PlaylistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;



import java.util.Optional;


@Service
public class PlaylistService {

    @Autowired
    private PlaylistsRepository playlistsRepo;

    // Check playlist name & create 
    public Playlists createPlaylist(Playlists newPlaylist, User owner, BindingResult result) {
	    if (playlistsRepo.existsByName(newPlaylist.getName())) {
	    	result.rejectValue("name", "Duplicate", "Playlist with this name already exists!");
	    	return null;
	    }
	
	    if (result.hasErrors()) {
	    	return null;
	    }
	
	        newPlaylist.setOwner(owner);
	        return playlistsRepo.save(newPlaylist);
	    }

    // Owner only - Update playlist name
    public Playlists updatePlaylist(Long playlistId, Playlists updatedPlaylist, User currentUser, BindingResult result) {
	    Optional<Playlists> existingPlaylistOpt = playlistsRepo.findById(playlistId);
	    if (!existingPlaylistOpt.isPresent()) {
		result.rejectValue("id", "NotFound", "No playlist found with the given ID.");
		return null;
	    }
	
		Playlists existingPlaylist = existingPlaylistOpt.get();
		if (!existingPlaylist.getOwner().equals(currentUser)) {
			result.rejectValue("owner", "Unauthorized", "You are not the owner of this playlist.");
			return null;
		 }
	
	        if (playlistsRepo.existsByNameAndIdNot(updatedPlaylist.getName(), playlistId)) {
	            result.rejectValue("name", "Duplicate", "Another playlist with this name already exists.");
	            return null;
	        }
	
	        if (result.hasErrors()) {
	            return null;
	        }
	
	        existingPlaylist.setName(updatedPlaylist.getName());
	        existingPlaylist.setDescription(updatedPlaylist.getDescription());
	
	        return playlistsRepo.save(existingPlaylist);
	    }
    
    // Owner Only - Update playlist description
    public Playlists updatePlaylistDetails(Long playlistId, Playlists updatedPlaylist, User owner, BindingResult result) {
        Optional<Playlists> playlistOpt = playlistsRepo.findById(playlistId);
        if (playlistOpt.isPresent()) {
            Playlists playlist = playlistOpt.get();
            if (playlist.getOwner().equals(owner)) {
                if (result.hasErrors()) {
                    return null;
                }
                playlist.setName(updatedPlaylist.getName());
                playlist.setDescription(updatedPlaylist.getDescription());
                return playlistsRepo.save(playlist);
            } else {
                result.rejectValue("owner", "Unauthorized", "You do not have permission to edit this playlist.");
                return null;
            }
        } else {
            result.rejectValue("id", "NotFound", "No playlist found with the given ID.");
            return null;
        }
    }

    // Owner only - delete playlist
    public void deletePlaylist(Long playlistId, User owner, BindingResult result) {
        Optional<Playlists> playlistOpt = playlistsRepo.findById(playlistId);
        if (playlistOpt.isPresent()) {
            Playlists playlist = playlistOpt.get();
            if (playlist.getOwner().equals(owner)) {
                playlistsRepo.delete(playlist);
            } else {
                result.rejectValue("owner", "Unauthorized", "You do not have permission to delete this playlist.");
            }
        } else {
            result.rejectValue("id", "NotFound", "No playlist found with the given ID.");
        }
    }

	    
    // Add track to playlist
    public void addTrackToPlaylist(Long playlistId, Tracks track, User user, BindingResult result) {
	    Optional<Playlists> playlistOpt = playlistsRepo.findById(playlistId);
	    if (playlistOpt.isPresent()) {
	    	Playlists playlist = playlistOpt.get();
	        playlist.getTracks().add(track);
	        playlistsRepo.save(playlist);
	    } else {
	    	result.rejectValue("id", "NotFound", "No playlist found with the given ID.");
	      }
	    }

    // Remove track from playlist
    public void removeTrackFromPlaylist(Long playlistId, Tracks track, User user, BindingResult result) {
	    Optional<Playlists> playlistOpt = playlistsRepo.findById(playlistId);
	    if (playlistOpt.isPresent()) {
	    	Playlists playlist = playlistOpt.get();
	    	if (playlist.getTracks().contains(track)) {
	    		playlist.getTracks().remove(track);
	    		playlistsRepo.save(playlist);
	    	} else {
	    		result.rejectValue("tracks", "NotFound", "The track is not present in this playlist.");
	    	  }
	        } else {
	            result.rejectValue("id", "NotFound", "No playlist found with the given ID.");
	          }
	    }
   

}