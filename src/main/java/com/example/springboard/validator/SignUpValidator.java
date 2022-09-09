package com.example.springboard.validator;

import com.example.springboard.domain.User;
import com.example.springboard.dto.UserRequest;
import com.example.springboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequest user = (UserRequest) target;

        String username = user.getUsername();
        User findUser = userRepository.findByUsername(username);

        if (findUser != null) {
            errors.rejectValue("username", "alreadyExist", "User name already exists");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "notEqual", "Passwords are not the same");
        }
    }
}
