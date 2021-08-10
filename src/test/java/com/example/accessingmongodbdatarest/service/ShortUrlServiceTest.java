package com.example.accessingmongodbdatarest.service;


import com.example.accessingmongodbdatarest.dao.MongoShortUrlDao;
import com.example.accessingmongodbdatarest.model.UrlDbEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;

class ShortUrlServiceTest
{
    @MockBean
    MongoShortUrlDao mongoRepository;

    @Test
    void shouldThrowWhenNoShortUrlSupplied()
    {
        Assertions.assertThrows(HttpClientErrorException.class, () ->
        {
            ShortUrlService service = new ShortUrlService();
            UrlDbEntry entry = new UrlDbEntry();
            service.getLongUrlByShort(entry.getShortUrl());
        });
    }

    @Test
    void shouldThrowWhenShortUrlEmpty()
    {
        Assertions.assertThrows(HttpClientErrorException.class, () ->
        {
            ShortUrlService service = new ShortUrlService();
            UrlDbEntry entry = new UrlDbEntry();
            entry.setShortUrl("");
            service.getLongUrlByShort(entry.getShortUrl());
        });
    }

    @Test
    void shouldThrowWhenNoLongUrlSupplied()
    {
        Assertions.assertThrows(HttpClientErrorException.class, () ->
        {
            ShortUrlService service = new ShortUrlService();
            UrlDbEntry entry = new UrlDbEntry();
            service.createShortUrl(entry.getLongUrl());
        });
    }

    @Test
    void shouldThrowWhenLongUrlEmpty()
    {
        Assertions.assertThrows(HttpClientErrorException.class, () ->
        {
            ShortUrlService service = new ShortUrlService();
            UrlDbEntry entry = new UrlDbEntry();
            entry.setLongUrl("");
            service.createShortUrl(entry.getLongUrl());
        });
    }


}