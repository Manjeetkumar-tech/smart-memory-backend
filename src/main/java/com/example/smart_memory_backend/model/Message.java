package com.example.smart_memory_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long itemId; // Reference to the item being discussed
    private String senderId; // User ID of sender
    private String receiverId; // User ID of receiver
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private LocalDateTime timestamp = LocalDateTime.now();
    private boolean read = false;
    
    // Constructors
    public Message() {}
    
    public Message(Long itemId, String senderId, String receiverId, String content) {
        this.itemId = itemId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    
    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}
