package com.example.springboard.dto;

import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import lombok.Getter;

import java.sql.Date;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final int views;
    private final Date date;
    private final String author;

    private final User user;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
        this.date = post.getDate();
        this.author = post.getAuthor();
        this.user = post.getUser();
    }
}
