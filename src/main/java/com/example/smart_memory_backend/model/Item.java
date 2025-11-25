package com.example.smart_memory_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private Double latitude;
    private Double longitude;
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    private ItemType type; // LOST or FOUND

    @Enumerated(EnumType.STRING)
    private ItemStatus status; // OPEN, CLAIMED, RESOLVED

    private String contactInfo;
    private String imageUrl;

    public Item() {}

    public Item(String title, String description, ItemType type, String location, LocalDate date) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.location = location;
        this.date = date;
        this.status = ItemStatus.OPEN;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public ItemType getType() { return type; }
    public void setType(ItemType type) { this.type = type; }

    public ItemStatus getStatus() { return status; }
    public void setStatus(ItemStatus status) { this.status = status; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
