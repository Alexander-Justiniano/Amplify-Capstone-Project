package edu.mdc.capstone.amplify.controllers;

import edu.mdc.capstone.amplify.models.Chat;
import edu.mdc.capstone.amplify.models.Message;
import edu.mdc.capstone.amplify.services.MessageService;
import edu.mdc.capstone.amplify.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatService chatService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @GetMapping("/messages/getMessages/{chatId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long chatId) {
        logger.info("Fetching messages for chatId: {}", chatId);
        List<Message> messages = messageService.findMessagesByChatId(chatId);
        return ResponseEntity.ok().body(messages);
    }


    // HTTP endpoint for sending messages
    @PostMapping("/messages")
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message.getChat().getChatId(), message.getContent(), message.getSenderId());
    }

    // WebSocket endpoint for receiving and processing messages
    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public Message processMessage(@Payload Message message, @DestinationVariable Long chatId) {
        logger.info("Message: {} {}", message, chatId);
        if (message.getChat() == null) {
            // Retrieve the Chat object from the database
            Chat chat = chatService.findById(chatId);
            if (chat == null) {
                throw new IllegalArgumentException("Chat not found for chatId: " + chatId);
            }
            // Set the retrieved Chat object to the Message
            message.setChat(chat);
        }
        logger.info("Received WebSocket message for chat {}: {}", chatId, message);
        return messageService.sendMessage(chatId, message.getContent(), message.getSenderId());
    }
}
