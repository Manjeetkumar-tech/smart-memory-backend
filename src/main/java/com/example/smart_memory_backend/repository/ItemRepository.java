package com.example.smart_memory_backend.repository;

import com.example.smart_memory_backend.model.Item;
import com.example.smart_memory_backend.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Combine Full-Text Search (stemming) AND Fuzzy Search (trigrams)
    @Query(value = "SELECT * FROM item WHERE " +
            "to_tsvector('english', coalesce(title, '') || ' ' || coalesce(description, '')) @@ plainto_tsquery(:q) " +
            "OR similarity(title, :q) > 0.1 " +
            "OR similarity(description, :q) > 0.1", 
            nativeQuery = true)
    List<Item> search(@Param("q") String query);
    List<Item> findByType(ItemType type);

    List<Item> findByUserId(String userId);

    @Query("SELECT i FROM Item i WHERE i.type <> :type AND i.category = :category AND i.status = 'OPEN'")
    List<Item> findPotentialMatches(@Param("type") ItemType type, @Param("category") String category);
}
