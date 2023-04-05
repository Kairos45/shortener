package com.tvacher.shortener.exception;

import com.tvacher.shortener.util.Constants;

public class ShortUrlInvalidException extends ApiException {
    public ShortUrlInvalidException(final String shortUrl) {
        super(shortUrl + Constants.SHORT_URL_INVALID_MESSAGE);
    }
}
