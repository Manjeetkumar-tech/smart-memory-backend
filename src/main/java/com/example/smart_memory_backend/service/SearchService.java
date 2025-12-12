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
        try {
            ItemDocument doc = new ItemDocument();
            doc.setId(item.getId());
            doc.setTitle(item.getTitle());
            doc.setDescription(item.getDescription());
            doc.setCategory(item.getCategory());
            itemSearchRepository.save(doc);
            System.out.println("Indexed item to Elasticsearch: " + item.getId());
        } catch (Exception e) {
            System.out.println("Error indexing item to Elasticsearch: " + e.getMessage());
        }
    }

    public void deleteItem(Long id) {
        try {
            itemSearchRepository.deleteById(id);
            System.out.println("Deleted item from Elasticsearch: " + id);
        } catch (Exception e) {
            System.out.println("Item not found in Elasticsearch or error deleting: " + e.getMessage());
        }
    }

    public List<ItemDocument> searchItems(String query) {
        // Fuzzy search on title and description using custom @Query
        return itemSearchRepository.fuzzySearch(query);
    }
}
