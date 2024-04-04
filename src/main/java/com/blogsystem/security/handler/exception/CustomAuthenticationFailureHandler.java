package com.blogsystem.security.handler.exception;

import com.blogsystem.common.response.APIResponseError;
import com.blogsystem.enums.ServiceErrorDesc;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        log.error("[Message]: " + authException.getMessage());
        var apiResponseError = APIResponseError.builder()
                .timestamp(new Date().getTime())
                .code(ServiceErrorDesc.UNAUTHORIZED_CODE.getVal())
                .message(authException.getMessage())
                .error(ServiceErrorDesc.UNAUTHORIZED_CODE.getDesc())
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponseError));
    }
}
