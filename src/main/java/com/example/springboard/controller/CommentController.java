package com.example.springboard.controller;

import com.example.springboard.domain.Comment;
import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import com.example.springboard.dto.CommentRequest;
import com.example.springboard.dto.PostResponse;
import com.example.springboard.service.PostService;
import com.example.springboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;
    private final UserService userService;


}
