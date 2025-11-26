package com.example.smart_memory_backend.controller;

import com.example.smart_memory_backend.model.Message;
import com.example.smart_memory_backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

    @GetMapping("/item/{itemId}")
    public List<Message> getMessagesByItem(@PathVariable Long itemId) {
        return messageRepository.findByItemIdOrderByTimestampAsc(itemId);
    }

    @GetMapping("/user/{userId}")
    public List<Message> getMessagesByUser(@PathVariable String userId) {
        return messageRepository.findBySenderIdOrReceiverIdOrderByTimestampDesc(userId, userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<Message> getUnreadMessages(@PathVariable String userId) {
        return messageRepository.findByReceiverIdAndReadFalse(userId);
    }

    @PutMapping("/{id}/read")
    public Message markAsRead(@PathVariable Long id) {
        Message message = messageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setRead(true);
        return messageRepository.save(message);
    }
}
