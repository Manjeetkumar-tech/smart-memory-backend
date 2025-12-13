package com.example.smart_memory_backend.controller;

import com.example.smart_memory_backend.model.Item;
import com.example.smart_memory_backend.model.ItemType;
import com.example.smart_memory_backend.model.ItemStatus;
import com.example.smart_memory_backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private com.example.smart_memory_backend.service.MatchingService matchingService;

    @Autowired
    private com.example.smart_memory_backend.service.SearchService searchService;

    @GetMapping("/{id}/matches")
    public List<Item> getMatches(@PathVariable Long id) {
        return matchingService.findMatches(id);
    }

    @GetMapping
    public List<Item> getAllItems(
            @RequestParam(required = false) ItemType type,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String search) {
        
        if (search != null && !search.isEmpty()) {
            // Use PostgreSQL Full-Text Search
            return searchService.searchItems(search);
        }

        if (userId != null && type != null) {
            return itemRepository.findAll().stream()
                .filter(item -> item.getUserId().equals(userId) && item.getType() == type)
                .toList();
        } else if (userId != null) {
            return itemRepository.findAll().stream()
                .filter(item -> item.getUserId().equals(userId))
                .toList();
        } else if (type != null) {
            return itemRepository.findByType(type);
        }
        return itemRepository.findAll();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        Item savedItem = itemRepository.save(item);
        // searchService.indexItem(savedItem); // Not needed with DB Triggers
        return savedItem;
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));
        
        item.setTitle(itemDetails.getTitle());
        item.setDescription(itemDetails.getDescription());
        item.setLocation(itemDetails.getLocation());
        item.setLatitude(itemDetails.getLatitude());
        item.setLongitude(itemDetails.getLongitude());
        item.setDate(itemDetails.getDate());
        item.setType(itemDetails.getType());
        item.setContactInfo(itemDetails.getContactInfo());
        item.setImageUrls(itemDetails.getImageUrls());
        item.setStatus(itemDetails.getStatus());
        item.setCategory(itemDetails.getCategory());
        
        Item updatedItem = itemRepository.save(item);
        // searchService.indexItem(updatedItem); // Not needed with DB Triggers
        return updatedItem;
    }


    @PutMapping("/{id}/claim")
    public Item claimItem(@PathVariable Long id, @RequestParam String userId) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setClaimedBy(userId);
        item.setClaimedAt(LocalDateTime.now());
        item.setStatus(ItemStatus.CLAIMED);
        
        Item updatedItem = itemRepository.save(item);
        // searchService.indexItem(updatedItem); // Not needed with DB Triggers
        return updatedItem;
    }

    @PutMapping("/{id}/resolve")
    public Item resolveItem(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setStatus(ItemStatus.RESOLVED);
        
        Item updatedItem = itemRepository.save(item);
        // searchService.indexItem(updatedItem); // Not needed with DB Triggers
        return updatedItem;
    }

    @PutMapping("/{id}/unclaim")
    public Item unclaimItem(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));
        
        if (item.getStatus() == ItemStatus.CLAIMED) {
            item.setStatus(ItemStatus.OPEN);
            item.setClaimedBy(null);
            item.setClaimedAt(null);
            
            Item updatedItem = itemRepository.save(item);
            // searchService.indexItem(updatedItem); // Not needed with DB Triggers
            return updatedItem;
        }
        return item;
    }

    @PutMapping("/{id}/unresolve")
    public Item unresolveItem(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));
        
        if (item.getStatus() == ItemStatus.RESOLVED) {
            item.setStatus(ItemStatus.CLAIMED);
            
            Item updatedItem = itemRepository.save(item);
            // searchService.indexItem(updatedItem); // Not needed with DB Triggers
            return updatedItem;
        }
        return item;
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        // searchService.deleteItem(id); // Not needed with DB Triggers
    }
}
