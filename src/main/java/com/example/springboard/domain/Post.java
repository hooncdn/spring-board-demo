package com.example.springboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Getter
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
    private Date date;
    private String author;
    private String status;

    @Builder
    public Post(User user, String title, String content, int views, Date date, String author) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.views = views;
        this.date = date;
        this.author = author;

        user.getPosts().add(this);

    }

    public void increaseViews(int views) {
        this.views += views;
    }
}
