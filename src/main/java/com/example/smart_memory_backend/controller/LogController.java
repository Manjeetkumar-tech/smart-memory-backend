package com.example.smart_memory_backend.controller;

import com.example.smart_memory_backend.model.Log;
import com.example.smart_memory_backend.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public Log createLog(@RequestBody Log log) {
        return logService.saveLog(log);
    }

    @GetMapping
    public List<Log> getLogsByUser(@RequestParam(required = false) String userId) {
        if (userId == null || userId.isEmpty()) {
            return logService.getAllLogs();  // Return all logs if no userId
        }
        return logService.getLogsByUserId(userId);  // Filter by userId if present
    }

    @DeleteMapping("/{id}")
    public void deleteLog(@PathVariable Long id) {
        logService.deleteLogById(id);
    }

    @PutMapping("/{id}")
    public Log updateLog(@PathVariable Long id, @RequestBody Log updatedLog) {
        return logService.updateLog(id, updatedLog);
    }
}