package com.example.springboard.controller;

import com.example.springboard.domain.Post;
import com.example.springboard.domain.Role;
import com.example.springboard.domain.Status;
import com.example.springboard.domain.User;
import com.example.springboard.dto.PostResponse;
import com.example.springboard.dto.UserRequest;
import com.example.springboard.dto.UserResponse;
import com.example.springboard.service.PostService;
import com.example.springboard.service.UserService;
import com.example.springboard.validator.SignUpValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final SignUpValidator signUpValidator;
    private final PasswordEncoder passwordEncoder;

    @InitBinder("UserRequest")
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(signUpValidator);
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("form", new UserResponse());
        return "user/register";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute(value = "form") UserRequest userRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(Role.USER)
                .status(Status.NORMAL)
                .build();


        userService.save(user);

        return "redirect:/";
    }

    @GetMapping("/my")
    public String myPage(Model model,
                         @PageableDefault(size = 5, sort = "views", direction = Sort.Direction.DESC)
                         Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        Page<Post> pageList = postService.findAllByUser(user, pageable);
        Page<PostResponse> posts = pageList.map(PostResponse::new);


        model.addAttribute("totalPages", posts.getTotalPages() - 1);
        model.addAttribute("posts", posts);

        return "/post/my";

    }
}
