package com.example.springboard.repository;

import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> findAllByTitle(String title, Pageable pageable);

}
