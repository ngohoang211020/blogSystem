package com.blogsystem.exception;

public class RestClientErrorException extends RuntimeException {
    public RestClientErrorException(String message) {
        super(message);
    }

    public RestClientErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
