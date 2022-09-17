package com.example.springboard.controller;

import com.example.springboard.domain.Post;
import com.example.springboard.dto.PostResponse;
import com.example.springboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping
    public String home(Model model,
                       @PageableDefault(size = 5, sort = "views", direction = Sort.Direction.DESC)
                       Pageable pageable) {

        Page<Post> pageList = postService.pageList(pageable);
        Page<PostResponse> posts = pageList.map(PostResponse::new);

        model.addAttribute("totalPages", posts.getTotalPages() - 1);
        model.addAttribute("posts", posts);

        return "home";
    }

}
