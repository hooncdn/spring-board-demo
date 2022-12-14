package com.example.springboard.controller;

import com.example.springboard.domain.Comment;
import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import com.example.springboard.dto.*;
import com.example.springboard.service.CommentService;
import com.example.springboard.service.PostService;
import com.example.springboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final CommentService commentService;

    @GetMapping
    public String home(Model model,
                       @PageableDefault(sort = "views", direction = Sort.Direction.DESC)
                       Pageable pageable) {

        Page<Post> postList = postService.findAll(pageable);
        Page<PostResponse> posts = postList.map(PostResponse::new);

        model.addAttribute("posts", posts);

        return "post/list";
    }

    @GetMapping("/{id}")
    public String viewForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        postService.increaseView(post);

        PostResponse postResponse = new PostResponse(post);
        CommentResponse commentResponse = new CommentResponse();
        List<CommentResponse> commentResponseList = post.getComments()
                .stream()
                .map(CommentResponse::new)
                .toList();

        model.addAttribute("post", postResponse);
        model.addAttribute("content", commentResponse);
        model.addAttribute("comment", new CommentResponse());
        model.addAttribute("comments", commentResponseList);

        return "post/view";
    }

    @PostMapping("/{id}")
    public String comment(@Valid @ModelAttribute(value = "content") CommentRequest commentRequest, BindingResult bindingResult, Model model, @PathVariable Long id) {

        Post post = postService.findById(id);

        if (bindingResult.hasErrors()) {
            PostResponse postResponse = new PostResponse(post);
            List<CommentResponse> commentResponseList = post.getComments()
                    .stream()
                    .map(CommentResponse::new)
                    .toList();

            model.addAttribute("post", postResponse);
            model.addAttribute("comment", new CommentResponse());
            model.addAttribute("comments", commentResponseList);

            return "post/view";
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss");

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .writer(user.getUsername())
                .content(commentRequest.getContent())
                .date(date.format(formatter))
                .build();


        commentService.uploadComment(id, comment);

        return "redirect:/post/{id}";
    }

    @PostMapping("/{id}/comment/{commentId}/update")
    public String updateComment(@Valid @ModelAttribute(value = "comment") CommentRequest commentRequest, BindingResult bindingResult, @PathVariable Long id, @PathVariable Long commentId, Model model) {

        if (bindingResult.hasErrors()) {
            Post post = postService.findById(id);

            PostResponse postResponse = new PostResponse(post);
            CommentResponse commentResponse = new CommentResponse();
            List<CommentResponse> commentResponseList = post.getComments()
                    .stream()
                    .map(CommentResponse::new)
                    .toList();

            model.addAttribute("post", postResponse);
            model.addAttribute("content", commentResponse);
            model.addAttribute("comments", commentResponseList);
            return "post/view";
        }

        commentService.updateComment(commentId, commentRequest.getContent());


        return "redirect:/post/{id}";
    }

    @PostMapping("/{id}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long id, @PathVariable Long commentId) {

        Comment comment = commentService.findByCommentId(commentId);

        if (comment == null) {
            return "error/404";
        }

        commentService.deleteComment(commentId);

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

    @GetMapping("/{id}/update")
    public String updateForm(Model model, @PathVariable Long id) {

        Post post = postService.findById(id);
        PostResponse postResponse = new PostResponse(post);

        model.addAttribute("post", postResponse);

        return "post/update";
    }

    @PostMapping("/{id}/update")
    public String update(@Valid @ModelAttribute(value = "post") PostRequest postRequest, BindingResult bindingResult, @PathVariable Long id) {

        if (bindingResult.hasErrors()) {
            return "post/update";
        }

        postService.update(id, postRequest.getTitle(), postRequest.getContent());

        return "redirect:/my";

    }

    @GetMapping("/{id}/delete")
    public String deleteForm(@PathVariable Long id, Model model) {

        model.addAttribute("id", id);
        return "post/delete";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        Post post = postService.findById(id);
        postService.delete(post);

        return "redirect:/my";
    }

    @GetMapping("/search")
    public String search(@RequestParam String title,
                         @PageableDefault(size = 5, sort = "views", direction = Sort.Direction.DESC) Pageable pageable,
                         Model model) {

        Page<Post> searchList = postService.findByTitleContaining(title, pageable);
        Page<PostResponse> posts = searchList.map(PostResponse::new);

        model.addAttribute("posts", posts);
        model.addAttribute("title", title);

        return "post/searchList";
    }

}
