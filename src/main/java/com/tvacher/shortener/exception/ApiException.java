package com.tvacher.shortener.exception;

public abstract class ApiException extends RuntimeException {
    public ApiException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public ApiException(String message) {
        this(message, null);
    }
}
