package com.tvacher.shortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvacher.shortener.exception.LongUrlInvalidException;
import com.tvacher.shortener.exception.ShortUrlInvalidException;
import com.tvacher.shortener.exception.ShortUrlNotFoundException;
import com.tvacher.shortener.exception.error.ApiError;
import com.tvacher.shortener.exception.handler.ShortenerExceptionHandler;
import com.tvacher.shortener.model.dto.LongUrlDto;
import com.tvacher.shortener.model.dto.ShortUrlDto;
import com.tvacher.shortener.service.ShortenerService;
import com.tvacher.shortener.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ShortenerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ShortenerController shortenerController;

    @Mock
    private ShortenerService shortenerService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(shortenerController)
                .setControllerAdvice(new ShortenerExceptionHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }


    @Test
    public void testShortenUrlOkStatus() throws Exception {
        ShortUrlDto shortUrlDto = ShortUrlDto.builder()
                .shortUrl("d431b34a")
                .build();
        when(shortenerService.findShortUrl(Mockito.anyString())).thenReturn(shortUrlDto);

        String body = new ObjectMapper().writeValueAsString(LongUrlDto.builder().longUrl("http://this-is-a-testing-url.com").build());

        MvcResult result = mockMvc.perform(put("/shorten")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andReturn();

        ShortUrlDto response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ShortUrlDto.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("d431b34a", response.getShortUrl());
    }

    @Test
    public void testShortenUrlInvalid() throws Exception {

        String body = new ObjectMapper().writeValueAsString(LongUrlDto.builder().longUrl("this-is-a-bad-url").build());

        mockMvc.perform(put("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testRetrieveLongUrlInvalid() throws Exception {

        mockMvc.perform(get("/test"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testRetrieveLongUrlNotFoundStatus() throws Exception {

        when(shortenerService.getUrlByShortUrl(Mockito.anyString())).thenThrow(ShortUrlNotFoundException.class);

        mockMvc.perform(get("/d421b34a"))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
