package com.example.springboard.dto;

import com.example.springboard.domain.Comment;
import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private int views;
    private Date date;
    private String author;

    private User user;

    private List<Comment> comments = new ArrayList<>();

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
        this.date = post.getDate();
        this.author = post.getAuthor();
        this.user = post.getUser();
        this.comments = post.getComments();
    }
}
