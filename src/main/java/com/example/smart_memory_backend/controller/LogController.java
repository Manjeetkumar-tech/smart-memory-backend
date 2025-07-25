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
        System.out.println("User ID: " + log.getUserId());
        return logService.saveLog(log);
    }

    @GetMapping
    public List<Log> getAllLogs() {
        return logService.getAllLogs();
    }
}