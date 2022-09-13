package com.example.springboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


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

    private String writer;
    private String content;
    private Date date;

    @Builder
    public Comment(Long id, User user, String writer, String content, Date date) {
        this.id = id;
        this.user = user;
        this.writer = writer;
        this.content = content;
        this.date = date;
    }
}
