package com.example.smart_memory_backend.service;

import com.example.smart_memory_backend.model.Item;
import com.example.smart_memory_backend.model.ItemDocument;
import com.example.smart_memory_backend.repository.ItemSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private ItemSearchRepository itemSearchRepository;

    public void indexItem(Item item) {
        ItemDocument doc = new ItemDocument(item);
        itemSearchRepository.save(doc);
        System.out.println("Indexed item to Elasticsearch: " + item.getId());
    }

    public void deleteItem(Long id) {
        itemSearchRepository.deleteById(id);
        System.out.println("Deleted item from Elasticsearch: " + id);
    }

    public List<ItemDocument> searchItems(String query) {
        // Fuzzy search on title and description using custom @Query
        return itemSearchRepository.fuzzySearch(query);
    }
}
