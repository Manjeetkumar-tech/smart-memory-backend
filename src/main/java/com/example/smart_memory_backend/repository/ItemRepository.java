package com.example.smart_memory_backend.repository;

import com.example.smart_memory_backend.model.Item;
import com.example.smart_memory_backend.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByType(ItemType type);

    List<Item> findByUserId(String userId);

    @Query("SELECT i FROM Item i WHERE i.type <> :type AND i.category = :category AND i.status = 'OPEN'")
    List<Item> findPotentialMatches(@Param("type") ItemType type, @Param("category") String category);
}
