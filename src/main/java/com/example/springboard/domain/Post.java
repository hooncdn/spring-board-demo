package com.example.springboard.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String title;
    private String content;
    private int views;
    private String author;

    private LocalDateTime postDate;

    @Builder
    public Post(Long id, String title, String content, String author, LocalDateTime postDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.postDate = postDate;
    }
}
