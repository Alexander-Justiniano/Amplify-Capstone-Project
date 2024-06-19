package edu.mdc.capstone.amplify.controllers;

import edu.mdc.capstone.amplify.models.Chat;
import edu.mdc.capstone.amplify.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/{userId}")
    public List<Chat> getChats(@PathVariable Long userId) {
        return chatService.findChatsByUserId(userId);
    }

    @PostMapping
    public Chat createOrGetChat(@RequestBody Chat chat) {
        return chatService.createOrGetChat(chat.getSenderId(), chat.getReceiverId());
    }
}
