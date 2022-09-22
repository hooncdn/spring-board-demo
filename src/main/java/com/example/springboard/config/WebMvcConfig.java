package com.example.springboard.config;

import com.example.springboard.inteceptor.CheckCommentWriterInterceptor;
import com.example.springboard.inteceptor.CheckPostAuthorInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final CheckPostAuthorInterceptor checkPostAuthorInterceptor;
    private final CheckCommentWriterInterceptor checkCommentWriterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkPostAuthorInterceptor)
                .addPathPatterns("/post/{id}/update/**", "/post/{id}/delete/**");
        registry.addInterceptor(checkCommentWriterInterceptor)
                .addPathPatterns("/post/{id}/comment/{commentId}/update/**", "/post/{id}/comment/{commentId}/delete/**");
    }
}
