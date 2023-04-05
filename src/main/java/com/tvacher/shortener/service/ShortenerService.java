package com.tvacher.shortener.service;

import com.tvacher.shortener.exception.ShortUrlNotFoundException;
import com.tvacher.shortener.model.dto.LongUrlDto;
import com.tvacher.shortener.model.dto.ShortUrlDto;
import com.tvacher.shortener.model.entity.Url;
import com.tvacher.shortener.repository.UrlRepository;
import com.tvacher.shortener.util.ShortenerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortenerService {

    Logger logger = LoggerFactory.getLogger(ShortenerService.class);

    @Autowired
    private UrlRepository urlRepository;

    /**
     * Retrieve the {@link Url} corresponding to the given long URL if it already exists. Otherwise, it creates a new entry in db.
     * @param longUrl long url provided by the user to be shortened.
     * @return {@link ShortUrlDto} representing the shorten URL
     */
    public ShortUrlDto findShortUrl(final String longUrl) {
        logger.info("looking for already existing long url : '{}'", longUrl);
        Url existingUrl = urlRepository.findUrlByLongUrl(longUrl);

        if(existingUrl == null) {
            logger.info("long url does not exist in db : '{}' ", longUrl);
            String shortUrl = ShortenerUtil.shortenLongUrl(longUrl);

            existingUrl = urlRepository.save(new Url(longUrl, shortUrl));
        }

        logger.info("long url: {} paired with shorten url : '{}' ", longUrl, existingUrl.getShortUrl());
        return ShortUrlDto.builder()
                .shortUrl(existingUrl.getShortUrl())
                .build();
    }

    /**
     * Retrieve the {@link Url} corresponding to the given short URL part if it exists.
     * @param shortUrl short url provided by the user.
     * @return {@link LongUrlDto} associated to the given short URL.
     */
    public LongUrlDto getUrlByShortUrl(final String shortUrl) {
        logger.info("looking for short url : '{}' in db", shortUrl);
        Url existingUrl = urlRepository.findUrlByShortUrl(shortUrl);

        if(existingUrl == null) {
            logger.error("{} : does not exist in db", shortUrl);
            throw new ShortUrlNotFoundException(shortUrl);
        }

        return LongUrlDto.builder()
                .longUrl(existingUrl.getLongUrl())
                .build();
    }

}
