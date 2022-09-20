package com.example.springboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String writer;
    private String content;
    private String date;

    @Builder
    public Comment(Long id, User user, Post post, String writer, String content, String date) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.writer = writer;
        this.content = content;
        this.date = date;

        user.getComments().add(this);
        post.getComments().add(this);
    }

    public void update(String content) {
        this.content = content;
    }
}
