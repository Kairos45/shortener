package com.tvacher.shortener.exception.handler;

import com.tvacher.shortener.exception.LongUrlInvalidException;
import com.tvacher.shortener.exception.ShortUrlInvalidException;
import com.tvacher.shortener.exception.error.ApiError;
import com.tvacher.shortener.exception.ShortUrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShortenerExceptionHandler {

    @ExceptionHandler(ShortUrlNotFoundException.class)
    protected ResponseEntity<Object> handleShortUrlNotFound(
            ShortUrlNotFoundException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(ShortUrlInvalidException.class)
    protected ResponseEntity<Object> handleShortUrlInvalid(
            ShortUrlInvalidException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(LongUrlInvalidException.class)
    protected ResponseEntity<Object> handleLongUrlInvalid(
            LongUrlInvalidException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
