package com.tvacher.shortener.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlDto implements Serializable {
    private String shortUrl;
}
