package com.example.springboard.controller;

import com.example.springboard.domain.User;
import com.example.springboard.dto.PostResponse;
import com.example.springboard.dto.UserResponse;
import com.example.springboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public String admin(Model model, @PageableDefault(size = 5, sort = "username", direction = Sort.Direction.DESC)
    Pageable pageable) {

        Page<User> pageList = userService.pageList(pageable);
        Page<UserResponse> users = pageList.map(UserResponse::new);

        model.addAttribute("totalPages", users.getTotalPages() - 1);
        model.addAttribute("users", users);

        return "dashboard/users";
    }

    @PostMapping("/ban/{username}")
    public String ban(@PathVariable String username) {
        User user = userService.findByUsername(username);
        userService.banUser(user);

        return "redirect:/dashboard/ban/{username}";
    }
}
