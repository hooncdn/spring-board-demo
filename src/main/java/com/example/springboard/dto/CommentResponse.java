package com.example.springboard.dto;

import com.example.springboard.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long id;

    private String writer;
    @NotBlank
    private String content;

    private String date;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
        this.date = comment.getDate();
    }

}
