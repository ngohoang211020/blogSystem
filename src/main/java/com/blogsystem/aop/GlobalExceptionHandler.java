package com.blogsystem.aop;

import com.blogsystem.common.constant.BlogSystemErrorCode;
import com.blogsystem.common.response.APIResponseError;
import com.blogsystem.exception.AccessDeniedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<APIResponseError> handleAuthenticationException(AccessDeniedException e) {
        log.error("[Message]: " + e.getMessage());
        var apiResponseError = APIResponseError.builder()
                .code(BlogSystemErrorCode.UNAUTHORIZED_CODE)
                .message(e.getMessage())
                .timestamp(new Date().getTime())
                .error("UNAUTHORIZED")
                .build();
        return new ResponseEntity<>(apiResponseError, HttpStatus.UNAUTHORIZED);
    }
}
