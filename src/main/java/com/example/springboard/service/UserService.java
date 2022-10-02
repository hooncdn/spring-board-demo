package com.example.springboard.service;

import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import com.example.springboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Page<User> pageList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public void banUser(User user) {
        user.banUser();
    }

    @Transactional
    public void unbanUser(User user) {
        user.unbanUser();
    }


}
