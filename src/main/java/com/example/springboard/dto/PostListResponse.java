package com.example.springboard.dto;

import com.example.springboard.domain.Post;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

@Getter
public class PostListResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final int views;
    private final Date date;
    private final String author;

    public PostListResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
        this.date = post.getDate();
        this.author = post.getAuthor();
    }

}
