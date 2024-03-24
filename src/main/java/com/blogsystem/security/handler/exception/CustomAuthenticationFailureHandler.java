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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        log.error("[Message]: " + authException.getMessage());
        var apiResponseError = APIResponseError.builder()
                .timestamp(new Date().getTime())
                .code(BlogSystemErrorCode.UNAUTHORIZED_CODE)
                .message(authException.getMessage())
                .error("UNAUTHORIZED")
                .build();

        response.getOutputStream().println(objectMapper.writeValueAsString(apiResponseError));
    }
}
