package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.Albums;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumsRepository extends CrudRepository<Albums, Long> {
	@Override
	List<Albums> findAll();
	
    Optional<Albums> findByTitle(String title);
}
