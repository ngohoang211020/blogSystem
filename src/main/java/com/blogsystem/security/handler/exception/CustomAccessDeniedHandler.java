package com.blogsystem.security.handler.exception;

import com.blogsystem.common.constant.BlogSystemErrorCode;
import com.blogsystem.common.response.APIResponseError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        log.error("[Message]: " + accessDeniedException.getMessage());
        var apiResponseError = APIResponseError.builder()
                .timestamp(new Date().getTime())
                .code(BlogSystemErrorCode.FORBIDDEN_CODE)
                .message(accessDeniedException.getMessage())
                .error("You don't have permission to access this")
                .build();

        response.getOutputStream().println(objectMapper.writeValueAsString(apiResponseError));
    }
}
