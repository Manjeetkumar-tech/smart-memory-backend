package com.example.smart_memory_backend.service;

import com.example.smart_memory_backend.model.Item;
import com.example.smart_memory_backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ItemRepository itemRepository;

    // No-op for compatibility or simple removal. Since we use DB triggers, we don't need manual indexing.
    public void indexItem(Item item) {
        // No operation needed for PostgreSQL full-text search with triggers
    }

    public void deleteItem(Long id) {
        // No operation needed
    }

    public List<Item> searchItems(String query) {
        // Use native PostgreSQL full-text search
        return itemRepository.search(query);
    }
}
