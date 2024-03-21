package com.blogsystem.exception;

public class OverLimitedException extends RuntimeException {
    public OverLimitedException(String message) {
        super(message);
    }

    public OverLimitedException(String message, Throwable cause) {
        super(message, cause);
    }
}