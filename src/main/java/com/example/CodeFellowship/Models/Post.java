package com.example.CodeFellowship.Models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private LocalDate createdAt;
    @ManyToOne
    private ApplicationUser userId;

    public Post()
    {

    }
    public Post(String body, LocalDate createdAt) {
        this.body = body;
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public ApplicationUser getUserId() {
        return userId;
    }
    public void setUserId(ApplicationUser userId) {
        this.userId = userId;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
