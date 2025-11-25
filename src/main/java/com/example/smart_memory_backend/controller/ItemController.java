package com.example.smart_memory_backend.controller;

import com.example.smart_memory_backend.model.Item;
import com.example.smart_memory_backend.model.ItemType;
import com.example.smart_memory_backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*") // Allow all for now, refine later
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<Item> getAllItems(@RequestParam(required = false) ItemType type) {
        if (type != null) {
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

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}
