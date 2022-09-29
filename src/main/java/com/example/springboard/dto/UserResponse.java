package com.example.springboard.dto;

import com.example.springboard.domain.Status;
import com.example.springboard.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String username;
    private String password;
    private String confirmPassword;

    private Status status;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getStatus();
    }
}
