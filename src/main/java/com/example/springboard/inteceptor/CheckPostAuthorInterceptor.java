package com.example.springboard.inteceptor;

import com.example.springboard.domain.Post;
import com.example.springboard.domain.User;
import com.example.springboard.service.PostService;
import com.example.springboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CheckPostAuthorInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final PostService postService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Object pathVariable = pathVariables.get("id");

        Long id = Long.valueOf(pathVariable.toString());

        if (!postService.validateId(id)) {
            response.sendRedirect("/error/404");
            return false;
        }

        Post post = postService.findById(id);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String author = post.getAuthor();

        User user = userService.findByUsername(username);

        if (!user.getRole().getValue().equals("ROLE_ADMIN")) {
            if (!username.equals(author)) {
                response.sendRedirect("/error/403");
                return false;
            }
        }

        return true;
    }
}
