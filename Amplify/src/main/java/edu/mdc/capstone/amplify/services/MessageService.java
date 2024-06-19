package edu.mdc.capstone.amplify.services;

import edu.mdc.capstone.amplify.models.Chat;
import edu.mdc.capstone.amplify.models.Message;
import edu.mdc.capstone.amplify.repositories.ChatRepository;
import edu.mdc.capstone.amplify.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    public List<Message> findMessagesByChatId(Long chatId) {
        return messageRepository.findByChat_ChatId(chatId);
    }

    public Message sendMessage(Long chatId, String content, Long senderId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        Message message = new Message();
        message.setChat(chat);
        message.setContent(content);
        message.setSenderId(senderId);
        message.setTime(LocalDateTime.now());
        return messageRepository.save(message);
    }
}
