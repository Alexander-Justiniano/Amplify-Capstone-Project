package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.AudioFile;
import edu.mdc.capstone.amplify.models.Song;
import edu.mdc.capstone.amplify.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
    List<Song> findByUser(User user);
}
