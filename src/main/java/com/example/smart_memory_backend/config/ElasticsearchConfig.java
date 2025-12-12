package com.example.smart_memory_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.lang.NonNull;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String uris;

    @Value("${spring.elasticsearch.username:}")
    private String username;

    @Value("${spring.elasticsearch.password:}")
    private String password;

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder = ClientConfiguration.builder()
                .connectedTo(uris.replace("https://", "").replace("http://", ""));

        // Add Basic Auth if credentials are provided
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            builder.withBasicAuth(username, password);
        }

        // Use SSL if connecting to HTTPS
        if (uris.startsWith("https")) {
            builder.usingSsl();
        }

        // CRITICAL: Enable compatibility mode for Bonsai (ES 7)
        return builder.withHeaders(() -> {
            org.springframework.data.elasticsearch.support.HttpHeaders headers = new org.springframework.data.elasticsearch.support.HttpHeaders();
            headers.add("Content-Type", "application/vnd.elasticsearch+json; compatible-with=7");
            return headers;
        }).build();
    }
}
