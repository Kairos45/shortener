package com.tvacher.shortener.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShortenerUtilTest {
    @Test
    public void testShortenLongUrl() {
        String longUrlToShorten = "http://this-is-a-testing-url.com";
        String result = ShortenerUtil.shortenLongUrl(longUrlToShorten);
        Assertions.assertTrue(result.length() < 10);
        Assertions.assertEquals("d431b34a", result);
    }

    @Test
    public void testShortenLongUrlTwice() {
        String longUrlToShorten = "http://this-is-a-testing-url.com";
        String result = ShortenerUtil.shortenLongUrl(longUrlToShorten);
        Assertions.assertEquals("d431b34a", result);

        // Hashing again to get the same result
        result = ShortenerUtil.shortenLongUrl(longUrlToShorten);
        Assertions.assertEquals("d431b34a", result);
    }

    @Test
    public void testShortenLongUrlWithDifferentValues() {
        String longUrlToShorten = "http://this-is-a-testing-url.com";
        String result = ShortenerUtil.shortenLongUrl(longUrlToShorten);
        Assertions.assertEquals("d431b34a", result);

        // Hashing again to get the same result
        longUrlToShorten = "http://this-is-another-testing-url.com";
        result = ShortenerUtil.shortenLongUrl(longUrlToShorten);
        Assertions.assertEquals("3b89f894", result);
    }

    @Test
    public void testIsValidShortUrlPartBlank() {
        String blankShortenUrlPart = " ";
        boolean result = ShortenerUtil.isValidShortUrlPart(blankShortenUrlPart);
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidShortUrlPartWithBlank() {
        String blankShortenUrlPart = "d431 34a";
        boolean result = ShortenerUtil.isValidShortUrlPart(blankShortenUrlPart);
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidShortUrlPartTooLong() {
        String blankShortenUrlPart = "3b89f8943b89f894";
        boolean result = ShortenerUtil.isValidShortUrlPart(blankShortenUrlPart);
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidShortUrlPartTooShort() {
        String blankShortenUrlPart = "3b89f";
        boolean result = ShortenerUtil.isValidShortUrlPart(blankShortenUrlPart);
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidShortUrlPartNull() {
        boolean result = ShortenerUtil.isValidShortUrlPart(null);
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidShortUrlPartNonAlphanumeric() {
        String blankShortenUrlPart = "d431b34@";
        boolean result = ShortenerUtil.isValidShortUrlPart(blankShortenUrlPart);
        Assertions.assertFalse(result);
    }
}
