package com.tvacher.shortener.exception;

import com.tvacher.shortener.util.Constants;

public class LongUrlInvalidException extends ApiException {
    public LongUrlInvalidException(final String longUrl) {
        super(longUrl + Constants.LONG_URL_INVALID_MESSAGE);
    }
}
