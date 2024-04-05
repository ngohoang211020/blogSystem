package com.blogsystem.aop;

import com.blogsystem.common.response.APIResponseError;
import com.blogsystem.enums.ServiceErrorDesc;
import com.blogsystem.exception.AccessDeniedException;
import com.blogsystem.exception.ObjectNotFoundException;
import com.blogsystem.exception.OverLimitedException;
import com.blogsystem.util.MapUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.validation.ConstraintDefinitionException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

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

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public APIResponseError serverExceptionHandler(final Exception exception) {
        logException(exception);

        return APIResponseError.builder()
                .code(ServiceErrorDesc.NOT_IMPLEMENTED.getVal())
                .message(exception.getMessage())
                .timestamp(new Date().getTime())
                .error(ServiceErrorDesc.NOT_IMPLEMENTED.getDesc())
                .build();
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(OverLimitedException.class)
    public APIResponseError rateLimitExceptionHandler(final OverLimitedException exception) {
        logException(exception);

        return APIResponseError.builder()
                .code(ServiceErrorDesc.OVER_RATE_LIMIT.getVal())
                .message(exception.getMessage())
                .timestamp(new Date().getTime())
                .error(ServiceErrorDesc.OVER_RATE_LIMIT.getDesc())
                .build();
    }
    private void logException(final @NonNull Exception exception) {
        log.error("Exception encountered: {}", exception.getMessage(), exception);
    }
}
