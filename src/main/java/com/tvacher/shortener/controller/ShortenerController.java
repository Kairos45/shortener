package com.tvacher.shortener.controller;

import com.tvacher.shortener.exception.LongUrlInvalidException;
import com.tvacher.shortener.exception.ShortUrlInvalidException;
import com.tvacher.shortener.exception.error.ApiError;
import com.tvacher.shortener.model.dto.LongUrlDto;
import com.tvacher.shortener.model.dto.ShortUrlDto;
import com.tvacher.shortener.service.ShortenerService;
import com.tvacher.shortener.util.ShortenerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tvacher.shortener.util.Constants.ALLOWED_SCHEMES;

@RestController
public class ShortenerController {

    Logger logger = LoggerFactory.getLogger(ShortenerController.class);

    @Autowired
    private ShortenerService shortenerService;


    @Operation(summary = "Shorten the given URL. Only HTTP and HTTPS are currently supported.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The shorten URL",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShortUrlDto.class)) }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid URL provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }) })
    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@Parameter(description = "the Long URL to shorten") @RequestBody final LongUrlDto longUrlDto) {
        String longUrl = longUrlDto.getLongUrl();

        UrlValidator validator = new UrlValidator(ALLOWED_SCHEMES);
        if(!validator.isValid(longUrl)) {
            logger.error("{} : is not a valid URL", longUrl);
            throw new LongUrlInvalidException(longUrl);
        }

        ShortUrlDto shortUrlDto = shortenerService.findShortUrl(longUrl);

        return ResponseEntity.ok(shortUrlDto);
    }

    @Operation(summary = "Retrieve the long URL associated with the short URL provided.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The long URL",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LongUrlDto.class)) }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid short URL part provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(
                    responseCode = "404",
                    description = "No long URL associated with the short part provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }) })
    @GetMapping("/{shortUrlPart}")
    public ResponseEntity<?> retrieveLongUrl(@Parameter(description = "the short URL part") @PathVariable(value = "shortUrlPart") final String shortUrlPart) {

        if(!ShortenerUtil.isValidShortUrlPart(shortUrlPart)) {
            logger.error("{} : is not a valid shortened URL", shortUrlPart);
            throw new ShortUrlInvalidException(shortUrlPart);
        }

        LongUrlDto existingLongUrl = shortenerService.getUrlByShortUrl(shortUrlPart);

        return ResponseEntity.ok(existingLongUrl);
    }
}
