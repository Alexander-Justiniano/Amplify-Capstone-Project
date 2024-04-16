package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {

}
