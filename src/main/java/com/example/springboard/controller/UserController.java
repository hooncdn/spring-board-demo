package com.example.springboard.controller;

import com.example.springboard.domain.User;
import com.example.springboard.domain.dto.SignUpForm;
import com.example.springboard.service.UserService;
import com.example.springboard.validator.SignUpValidator;
import lombok.RequiredArgsConstructor;
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

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SignUpValidator signUpValidator;
    private final PasswordEncoder passwordEncoder;

    @InitBinder
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
    public String getJoin(Model model) {
        model.addAttribute("form", new SignUpForm());
        return "user/register";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute(value = "form") SignUpForm signUpForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        User user = User.builder()
                .username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .role("USER")
                .build();


        userService.createUser(user);

        return "redirect:/";
    }
}
