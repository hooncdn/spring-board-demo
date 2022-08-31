package com.example.springboard.controller;

import com.example.springboard.domain.dto.SignUpForm;
import com.example.springboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

        return "redirect:/";
    }
}
