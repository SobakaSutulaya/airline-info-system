package com.example.airlineinfosystem3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название самолёта, например "Boeing 737-800"
    @Column(nullable = false, length = 100)
    private String name;

    // Путь к картинке, например "/images/aircraft/boeing737.jpg"
    @Column(length = 255)
    private String imageUrl;

    // Краткая подпись под карточкой
    @Column(length = 255)
    private String shortInfo;

    // Подробное описание
    @Lob
    private String details;

    public Aircraft() {
    }

    public Aircraft(String name, String imageUrl, String shortInfo, String details) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.shortInfo = shortInfo;
        this.details = details;
    }

    // getters / setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public String getDetails() {
        return details;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
