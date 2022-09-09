package com.example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class UserRequest {

    @NotBlank(message = "Can not be blank")
    @Length(min = 3,max = 15, message = "Must be between 3 and 15 in length")
    private String username;

    @NotBlank(message = "Can not be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$" , message = "It must contain at least eight characters, at least one uppercase letter, one lowercase letter, and one number")
    private String password;

    @NotBlank(message = "Can not be blank")
    private String confirmPassword;
}
