package com.example.springboard.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    @GetMapping("/users")
    public String admin(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
                        Pageable pageable) {
        return "dashboard/users";
    }
}
