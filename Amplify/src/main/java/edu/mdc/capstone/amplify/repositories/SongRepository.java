package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.Song;
import edu.mdc.capstone.amplify.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByUser(User user);
}


