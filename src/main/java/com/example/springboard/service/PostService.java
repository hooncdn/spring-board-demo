package com.example.springboard.service;

import com.example.springboard.domain.Post;
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

    public Post findById(Long id) {
        return postRepository.findById(id)
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

    @Transactional
    public Page<Post> pageList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public boolean validateId(Long id) {

        Optional<Post> post = postRepository.findById(id);

        return post.isPresent();

    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }


}
