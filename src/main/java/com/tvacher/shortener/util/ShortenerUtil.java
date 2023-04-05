package com.tvacher.shortener.util;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

public class ShortenerUtil {

    private static final int SHORT_URL_PART_LENGTH = 8;

    /**
     * Shorten the given long URL using { @link Hashing#murmur3_32_fixed() } to hash it
     * @param longUrl the given URL to shorten
     * @return 8 characters shorten URL part
     */
    public static String shortenLongUrl(final String longUrl) {
        return Hashing.murmur3_32_fixed().hashString(longUrl, StandardCharsets.UTF_8).toString();
    }

    /**
     * Validate the provided short URL part respects the following predicates:
     *  - not empty
     *  - no blanks
     *  - should be 8 characters long
     *  - contains only alphanumeric characters
     * @param shortUrlPart short URL part provided by the user
     * @return true if the given URL complies, false otherwise
     */
    public static boolean isValidShortUrlPart(final String shortUrlPart) {
        return StringUtils.isNotBlank(shortUrlPart)
                && StringUtils.isNoneBlank(shortUrlPart)
                && StringUtils.length(shortUrlPart) == SHORT_URL_PART_LENGTH
                && StringUtils.isAlphanumeric(shortUrlPart);
    }
}
