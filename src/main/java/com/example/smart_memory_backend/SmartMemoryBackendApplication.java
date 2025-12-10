package com.example.smart_memory_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartMemoryBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartMemoryBackendApplication.class, args);
	}

    @org.springframework.context.annotation.Bean
    public org.springframework.boot.CommandLineRunner updateDatabaseSchema(org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                jdbcTemplate.execute("ALTER TABLE item ALTER COLUMN description TYPE TEXT");
                System.out.println("✅ Successfully altered 'description' column to TEXT");
            } catch (Exception e) {
                System.out.println("⚠️ Could not alter column (might already be TEXT): " + e.getMessage());
            }
        };
    }
}
