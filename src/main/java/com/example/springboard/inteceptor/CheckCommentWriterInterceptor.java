package com.example.springboard.inteceptor;

import com.example.springboard.domain.Comment;
import com.example.springboard.service.PostService;
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
public class CheckCommentWriterInterceptor implements HandlerInterceptor {

    private final PostService postService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Object pathVariable = pathVariables.get("commentId");

        Long commentId = Long.valueOf(pathVariable.toString());

        if (!postService.validateCommentId(commentId)) {
            response.sendRedirect("/error/404");
            return false;
        }

        Comment comment = postService.findByCommentId(commentId);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String writer = comment.getWriter();

        if (!username.equals(writer)) {
            response.sendRedirect("/error/403");
            return false;
        }


        return true;

    }
}
