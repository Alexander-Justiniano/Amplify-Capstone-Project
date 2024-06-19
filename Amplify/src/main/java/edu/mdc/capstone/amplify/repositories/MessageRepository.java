package edu.mdc.capstone.amplify.repositories;

import edu.mdc.capstone.amplify.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChat_ChatIdOrderByTimeDesc(Long chatId);
    List<Message> findByChat_ChatId(Long chatId);
}

