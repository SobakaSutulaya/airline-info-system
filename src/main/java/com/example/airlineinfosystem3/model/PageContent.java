package com.example.airlineinfosystem3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "page_content")
public class PageContent {

    @Id
    @Column(name="page_key", nullable = false, length = 50)
    private String pageKey; // HOME / ABOUT / RADAR

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TINYTEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    public PageContent() {}

    public PageContent(String pageKey, String title, String content, String body) {
        this.pageKey = pageKey;
        this.title = title;
        this.content = content;
        this.body = body;
    }

    public String getPageKey() { return pageKey; }
    public void setPageKey(String pageKey) { this.pageKey = pageKey; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
