package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.Artists;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistsRepository extends CrudRepository<Artists, Long> {
	Optional<Artists> findByName(String name);
}
