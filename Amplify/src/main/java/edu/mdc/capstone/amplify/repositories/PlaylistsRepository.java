package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.Playlists;
import edu.mdc.capstone.amplify.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistsRepository extends CrudRepository<Playlists, Long> {

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    Optional<Playlists> findByName(String name);
    List<Playlists> findByOwner(User owner);

}
