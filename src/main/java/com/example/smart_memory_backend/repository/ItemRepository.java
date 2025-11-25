package com.example.smart_memory_backend.repository;

import com.example.smart_memory_backend.model.Item;
import com.example.smart_memory_backend.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByType(ItemType type);
}
