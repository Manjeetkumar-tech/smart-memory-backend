package com.example.smart_memory_backend.repository;

import com.example.smart_memory_backend.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByUserId(String userId);
    List<Log> findByUserIdOrderByTimestampDesc(String userId);
    List<Log> findAllByOrderByTimestampDesc();
}