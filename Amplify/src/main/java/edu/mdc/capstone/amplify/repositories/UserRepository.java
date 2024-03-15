package edu.mdc.capstone.amplify.repositories;
import edu.mdc.capstone.amplify.models.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	List<User> findAll();
	
	Optional<User> findByEmail(String email);
	
	User findUserByEmail(String email);

}
