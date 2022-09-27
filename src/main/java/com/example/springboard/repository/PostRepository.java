package com.example.springboard.repository;

import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Page<Post> findAllByUser(User user, Pageable pageable);
    Page<Post> findAllByTitle(String title, Pageable pageable);
}
