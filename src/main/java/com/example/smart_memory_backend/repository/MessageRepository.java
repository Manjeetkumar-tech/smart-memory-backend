package com.example.smart_memory_backend.repository;

import com.example.smart_memory_backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByItemIdOrderByTimestampAsc(Long itemId);
    List<Message> findByReceiverIdOrderByTimestampDesc(String receiverId);
    List<Message> findBySenderIdOrReceiverIdOrderByTimestampDesc(String senderId, String receiverId);
    List<Message> findByReceiverIdAndReadFalse(String receiverId);
}
