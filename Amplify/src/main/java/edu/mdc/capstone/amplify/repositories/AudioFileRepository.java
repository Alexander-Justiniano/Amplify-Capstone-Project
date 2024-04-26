package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.AudioFile;
import edu.mdc.capstone.amplify.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {
    List<AudioFile> findByUser(User user);
}
