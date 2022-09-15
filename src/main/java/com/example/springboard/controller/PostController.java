package com.example.springboard.controller;

import com.example.springboard.domain.Comment;
import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import com.example.springboard.dto.CommentRequest;
import com.example.springboard.dto.CommentResponse;
import com.example.springboard.dto.PostRequest;
import com.example.springboard.dto.PostResponse;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/{id}")
    public String viewForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        postService.increaseView(post);

        PostResponse postResponse = new PostResponse(post);
        CommentResponse commentResponse = new CommentResponse();

        model.addAttribute("post", postResponse);
        model.addAttribute("comment", commentResponse);

        return "post/view";
    }

    @PostMapping("/{id}")
    public String comment(@Valid @ModelAttribute(value = "comment") CommentRequest commentRequest, BindingResult bindingResult, Model model, @PathVariable Long id) {

        Post post = postService.findById(id);

        if (bindingResult.hasErrors()) {
            PostResponse postResponse = new PostResponse(post);
            model.addAttribute("post", postResponse);
            return "/post/view";
        }

        User user = post.getUser();

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss");

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .writer(user.getUsername())
                .content(commentRequest.getContent())
                .date(date.format(formatter))
                .build();


        postService.uploadComment(id, comment);

        return "redirect:/post/{id}";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("post", new PostResponse());
        return "post/upload";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute(value = "post") PostRequest postRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "post/upload";
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(username);

        long millis = System.currentTimeMillis();
        Date date = new Date(millis);

        Post post = Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .views(0)
                .date(date)
                .author(username)
                .build();

        postService.save(post);

        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(Model model, @PathVariable Long id) {

        Post post = postService.findById(id);
        PostResponse postResponse = new PostResponse(post);

        model.addAttribute("post", postResponse);

        return "post/update";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute(value = "post") PostRequest postRequest, BindingResult bindingResult, @PathVariable Long id) {

        if (bindingResult.hasErrors()) {
            return "post/update";
        }

        postService.update(id, postRequest.getTitle(), postRequest.getContent());

        return "redirect:/my";

    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Long id, Model model) {

        model.addAttribute("id", id);
        return "post/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        Post post = postService.findById(id);
        postService.delete(post);

        return "redirect:/my";
    }

}
