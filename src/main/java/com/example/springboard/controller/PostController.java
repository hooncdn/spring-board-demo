package com.example.springboard.controller;

import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import com.example.springboard.domain.dto.UploadPostForm;
import com.example.springboard.repository.PostRepository;
import com.example.springboard.repository.UserRepository;
import com.example.springboard.service.PostService;
import com.example.springboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);

        model.addAttribute("post", post);

        return "post/view";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        model.addAttribute("form", new UploadPostForm());
        return "post/upload";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute(value = "form") UploadPostForm uploadPostForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "post/upload";
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        long millis = System.currentTimeMillis();
        Date date = new Date(millis);

        Post post = Post.builder()
                .user(user)
                .title(uploadPostForm.getTitle())
                .content(uploadPostForm.getContent())
                .views(0)
                .date(date)
                .author(username)
                .build();

        postService.save(post);
        return "redirect:/";
    }
}
