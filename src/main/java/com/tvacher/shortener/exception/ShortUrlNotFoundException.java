package com.tvacher.shortener.exception;


import com.tvacher.shortener.util.Constants;

public class ShortUrlNotFoundException extends ApiException {
    public ShortUrlNotFoundException(final String shortUrl) {
        super(shortUrl + Constants.SHORT_URL_NOT_FOUND_MESSAGE);
    }
}
