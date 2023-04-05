package com.tvacher.shortener.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LongUrlDto implements Serializable {

    @NotNull
    @Size(max = 2048)
    private String longUrl;
}
