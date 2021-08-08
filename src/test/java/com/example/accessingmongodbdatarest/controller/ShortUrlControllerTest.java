package com.example.accessingmongodbdatarest.controller;

import com.example.accessingmongodbdatarest.model.UrlDbEntry;
import com.example.accessingmongodbdatarest.service.ShortUrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShortUrlController.class)
class ShortUrlControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objMapper;

    @MockBean
    ShortUrlService urlService;

    @Test
    void shouldGetAllUrlEntries()
            throws Exception
    {
        final String apiPath = "/shortUrl/allUrls";
        final UrlDbEntry entryOne = createDbEntry("a", "test.com");
        final UrlDbEntry entryTwo = createDbEntry("b", "testTwo.com");
        final List<UrlDbEntry> dbEntries = new ArrayList<>(Arrays.asList(entryOne, entryTwo));

        when(urlService.getUrls()).thenReturn(dbEntries);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(apiPath)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].longUrl", is("testTwo.com")));
    }

    @Test
    void shouldCreateShortUrlEntry()
            throws Exception
    {
        final String apiPath = "/shortUrl/create";

        final UrlDbEntry expectedEntry = createDbEntry("a", "test.com");
        when(urlService.createShortUrl(expectedEntry.getLongUrl())).thenReturn(expectedEntry);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objMapper.writeValueAsString(expectedEntry));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.shortUrl", is(expectedEntry.getShortUrl())))
                .andExpect(jsonPath("$.longUrl", is(expectedEntry.getLongUrl())));
    }

    @Test
    void shouldGetLongUrlFromShortUrl()
            throws Exception
    {
        final String apiPath = "/shortUrl/getLongFromShort";

        final UrlDbEntry expectedEntry = createDbEntry("a", "test.com");
        when(urlService.getLongUrlByShort(expectedEntry.getShortUrl())).thenReturn(expectedEntry);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objMapper.writeValueAsString(expectedEntry));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.shortUrl", is(expectedEntry.getShortUrl())))
                .andExpect(jsonPath("$.longUrl", is(expectedEntry.getLongUrl())));
    }

    private UrlDbEntry createDbEntry(final String shortUrl, final String longUrl)
    {
        UrlDbEntry entry = new UrlDbEntry();
        entry.setShortUrl(shortUrl);
        entry.setLongUrl(longUrl);
        return entry;
    }
}