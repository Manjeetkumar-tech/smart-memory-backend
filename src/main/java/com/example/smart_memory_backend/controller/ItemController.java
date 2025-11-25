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

    @GetMapping
    public List<Item> getAllItems(
            @RequestParam(required = false) ItemType type,
            @RequestParam(required = false) String userId) {
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
        return itemRepository.save(item);
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }


    @PutMapping("/{id}/claim")
    public Item claimItem(@PathVariable Long id, @RequestParam String userId) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setClaimedBy(userId);
        item.setClaimedAt(LocalDateTime.now());
        item.setStatus(ItemStatus.CLAIMED);
        return itemRepository.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}
