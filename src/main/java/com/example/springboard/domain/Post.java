package com.example.springboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private String title;
    private String content;
    private int views;
    private Date date;
    private String author;
    private String status;

    @Builder
    public Post(Long id, User user, String title, String content, int views, Date date, String author) {
        this.id = id;
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

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
