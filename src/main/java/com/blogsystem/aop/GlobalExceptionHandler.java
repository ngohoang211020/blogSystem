package com.blogsystem.aop;

import com.blogsystem.common.response.APIResponseError;
import com.blogsystem.enums.ServiceErrorDesc;
import com.blogsystem.exception.AccessDeniedException;
import com.blogsystem.exception.ObjectNotFoundException;
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
                .code(ServiceErrorDesc.UNAUTHORIZED_CODE.getVal())
                .message(e.getMessage())
                .timestamp(new Date().getTime())
                .error(ServiceErrorDesc.UNAUTHORIZED_CODE.getDesc())
                .build();
        return new ResponseEntity<>(apiResponseError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    protected ResponseEntity<APIResponseError> handleObjectNotFoundException(AccessDeniedException e) {
        log.error("[Message]: " + e.getMessage());
        var apiResponseError = APIResponseError.builder()
                .code(ServiceErrorDesc.RESOURCE_NOT_FOUND.getVal())
                .message(e.getMessage())
                .timestamp(new Date().getTime())
                .error(ServiceErrorDesc.RESOURCE_NOT_FOUND.getDesc())
                .build();
        return new ResponseEntity<>(apiResponseError, HttpStatus.NOT_FOUND);
    }
}
