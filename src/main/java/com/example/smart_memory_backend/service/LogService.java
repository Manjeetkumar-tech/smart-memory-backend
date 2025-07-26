package com.example.smart_memory_backend.service;

import com.example.smart_memory_backend.model.Log;
import com.example.smart_memory_backend.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Log saveLog(Log log) {
        return logRepository.save(log);
    }

    public List<Log> getLogsByUserId(String userId) {
        return logRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    public List<Log> getAllLogs() {
        return logRepository.findAllByOrderByTimestampDesc();
    }

    public void deleteLogById(Long id) {
        logRepository.deleteById(id);
    }

    public Log updateLog(Long id, Log updatedLog) {
        return logRepository.findById(id).map(log -> {
            log.setContent(updatedLog.getContent());
            log.setMood(updatedLog.getMood());
            log.setCategory(updatedLog.getCategory());
            return logRepository.save(log);
        }).orElseThrow(() -> new RuntimeException("Log not found with id " + id));
    }
}