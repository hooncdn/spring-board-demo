package com.example.springboard.controller;

import com.example.springboard.domain.Post;
import com.example.springboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping
    public String home(Model model) {

        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        return "home";
    }

}
