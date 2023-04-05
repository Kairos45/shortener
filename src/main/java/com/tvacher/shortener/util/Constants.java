package com.tvacher.shortener.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Constants {
    public static final String[] ALLOWED_SCHEMES = {"http", "https"};

    public static final String SHORT_URL_NOT_FOUND_MESSAGE = " is not linked to a long URL.";
    public static final String SHORT_URL_INVALID_MESSAGE = " is not a valid short URL.";
    public static final String LONG_URL_INVALID_MESSAGE = " is not a valid long URL.";
}
