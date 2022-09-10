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
public class PostRequest {

    private Long id;

    @NotBlank(message = "Can not be blank")
    @Length(min = 3, max = 20, message = "Must be between 3 and 20 in length")
    private String title;

    @NotBlank(message = "Can not be blank")
    @Length(max = 2000, message = "Must be between 10 and 1000 in length")
    private String content;


}
