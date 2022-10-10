package com.example.springboard.service;

import com.example.springboard.domain.Comment;
import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import com.example.springboard.repository.CommentRepository;
import com.example.springboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void save(Post post) {
        postRepository.save(post);
    }

    @Transactional
    public void update(Long id, String title, String content) {
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The post is missing"));

        findPost.update(title, content);

    }

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

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Comment findByCommentId(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public void increaseView(Post post) {
        post.increaseViews(1);
    }

    public Page<Post> pageList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public boolean validateId(Long id) {

        Optional<Post> post = postRepository.findById(id);

        return post.isPresent();

    }
    public boolean validateCommentId(Long id) {

        Optional<Comment> comment = commentRepository.findById(id);

        return comment.isPresent();

    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(IllegalAccessError::new);
        commentRepository.delete(comment);
    }

    public Page<Post> findAllByUser(User user, Pageable pageable) {
        return postRepository.findAllByUser(user, pageable);
    }

}
