package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<Chat> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
