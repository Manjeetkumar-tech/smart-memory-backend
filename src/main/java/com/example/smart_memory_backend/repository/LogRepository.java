package com.example.smart_memory_backend.repository;

import com.example.smart_memory_backend.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}