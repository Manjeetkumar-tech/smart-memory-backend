package com.example.smart_memory_backend.repository;

import com.example.smart_memory_backend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}