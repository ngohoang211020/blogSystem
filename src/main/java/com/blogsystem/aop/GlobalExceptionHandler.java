package com.blogsystem.aop;

import com.blogsystem.common.response.APIResponseError;
import com.blogsystem.enums.ServiceErrorDesc;
import com.blogsystem.exception.AccessDeniedException;
import com.blogsystem.exception.ObjectNotFoundException;
import com.blogsystem.util.MapUtil;
import jakarta.validation.ConstraintDefinitionException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {UsernameNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected APIResponseError handleAuthenticationException(UsernameNotFoundException e) {
        log.error("[Message]: " + e.getMessage());
        return APIResponseError.builder()
                .code(ServiceErrorDesc.UNAUTHORIZED_CODE.getVal())
                .message(e.getMessage())
                .timestamp(new Date().getTime())
                .error(ServiceErrorDesc.UNAUTHORIZED_CODE.getDesc())
                .build();
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected APIResponseError handleAccessDeniedException(AccessDeniedException e) {
        log.error("[Message]: " + e.getMessage());
        return APIResponseError.builder()
                .code(ServiceErrorDesc.FORBIDDEN_CODE.getVal())
                .message(e.getMessage())
                .timestamp(new Date().getTime())
                .error(ServiceErrorDesc.FORBIDDEN_CODE.getDesc())
                .build();
    }

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected APIResponseError handleObjectNotFoundException(ObjectNotFoundException e) {
        log.error("[Message]: " + e.getMessage());
        return APIResponseError.builder()
                .code(ServiceErrorDesc.RESOURCE_NOT_FOUND.getVal())
                .message(e.getMessage())
                .timestamp(new Date().getTime())
                .error(ServiceErrorDesc.RESOURCE_NOT_FOUND.getDesc())
                .build();
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ConstraintDefinitionException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponseError handleBindException(Exception e) {
        var strBuilder = new StringBuilder();
        if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName;
                try {
                    fieldName = ((FieldError) error).getField();

                } catch (ClassCastException ex) {
                    fieldName = error.getObjectName();
                }
                String message = error.getDefaultMessage();
                strBuilder.append(String.format("%s: %s\n", fieldName, message));
            });
        } else if (e instanceof ConstraintDefinitionException constraintDefinitionException) {
            strBuilder.append(constraintDefinitionException.getMessage());
        }

        return APIResponseError.builder()
                .code(ServiceErrorDesc.BAD_FORMAT.getVal())
                .message(strBuilder.substring(0, strBuilder.length() - 1))
                .timestamp(new Date().getTime())
                .error(ServiceErrorDesc.BAD_FORMAT.getDesc())
                .build();
    }

}
