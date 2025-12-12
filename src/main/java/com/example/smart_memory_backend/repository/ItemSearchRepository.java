package com.example.smart_memory_backend.repository;

import com.example.smart_memory_backend.model.ItemDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSearchRepository extends ElasticsearchRepository<ItemDocument, Long> {
    List<ItemDocument> findByTitleContainingOrDescriptionContaining(String title, String description);

    @org.springframework.data.elasticsearch.annotations.Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title\", \"description\"], \"fuzziness\": \"AUTO\"}}")
    List<ItemDocument> fuzzySearch(String query);
}
