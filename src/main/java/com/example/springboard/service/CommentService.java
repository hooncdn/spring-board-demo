package com.example.springboard.service;

import com.example.springboard.domain.Comment;
import com.example.springboard.domain.Post;
import com.example.springboard.repository.CommentRepository;
import com.example.springboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void updateComment(Long id, String content) {
        Comment findComment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The comment is missing"));
        findComment.update(content);
    }

    @Transactional
    public void uploadComment(Long id, Comment comment) {
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The content is missing"));

        findPost.addComment(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(IllegalAccessError::new);
        commentRepository.delete(comment);
    }

    public boolean validateCommentId(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.isPresent();
    }

    public Comment findByCommentId(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
