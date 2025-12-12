package com.example.smart_memory_backend.service;

import com.example.smart_memory_backend.model.Item;
import com.example.smart_memory_backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> findMatches(Long itemId) {
        Item sourceItem = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        // 1. Find candidates: Opposite Type + Same Category + Status OPEN
        System.out.println("Finding matches for Item ID: " + itemId + ", Type: " + sourceItem.getType() + ", Category: " + sourceItem.getCategory());
        List<Item> candidates = itemRepository.findPotentialMatches(sourceItem.getType(), sourceItem.getCategory());
        System.out.println("Found " + candidates.size() + " candidates from DB.");

        // 2. Filter by Keyword Overlap (Simple Logic)
        return candidates.stream()
                .filter(candidate -> {
                    boolean match = hasKeywordOverlap(sourceItem, candidate);
                    System.out.println("Candidate ID: " + candidate.getId() + " (" + candidate.getTitle() + ") - Match: " + match);
                    return match;
                })
                .collect(Collectors.toList());
    }

    private boolean hasKeywordOverlap(Item source, Item candidate) {
        String sourceText = (source.getTitle() + " " + source.getDescription()).toLowerCase();
        String candidateText = (candidate.getTitle() + " " + candidate.getDescription()).toLowerCase();

        // Split source title into words and check if any significant word appears in candidate text
        String[] keywords = source.getTitle().split("\\s+");
        for (String word : keywords) {
            if (word.length() > 3 && candidateText.contains(word.toLowerCase())) {
                return true; // Match found!
            }
        }
        return false;
    }
}
