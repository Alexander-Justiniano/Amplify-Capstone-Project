package edu.mdc.capstone.amplify.services;

import edu.mdc.capstone.amplify.models.Chat;
import edu.mdc.capstone.amplify.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> findChatsByUserId(Long userId) {
        return chatRepository.findBySenderIdOrReceiverId(userId, userId);
    }

    public Optional<Chat> findExistingChat(Long senderId, Long receiverId) {
        return chatRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .or(() -> chatRepository.findBySenderIdAndReceiverId(receiverId, senderId));
    }

    public Chat createOrGetChat(Long senderId, Long receiverId) {
        Optional<Chat> existingChat = findExistingChat(senderId, receiverId);
        if (existingChat.isPresent()) {
            return existingChat.get();
        } else {
            Chat chat = new Chat();
            chat.setSenderId(senderId);
            chat.setReceiverId(receiverId);
            return chatRepository.save(chat);
        }
    }

    public Chat findById(Long id) {
        return chatRepository.findById(id).orElse(null);
    }
}
