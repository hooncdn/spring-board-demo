package com.example.springboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    private Long id;

    @NotBlank(message = "Can not be blank")
    @Length(max = 100, message = "Max length is 100")
    private String content;
}