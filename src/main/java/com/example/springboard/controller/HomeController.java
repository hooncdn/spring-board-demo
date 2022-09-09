package com.example.springboard.controller;

import com.example.springboard.domain.Post;
import com.example.springboard.dto.PostListResponse;
import com.example.springboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping
    public String home(Model model) {

        List<PostListResponse> postList = postService.findAll()
                .stream()
                .map(PostListResponse::new)
                .toList();

        model.addAttribute("posts", postList);

        return "home";
    }

}
