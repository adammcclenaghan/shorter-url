package com.example.accessingmongodbdatarest.controller;

import com.example.accessingmongodbdatarest.model.ShortUrl;
import com.example.accessingmongodbdatarest.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RepositoryRestController
@RequestMapping("/shortUrl")
@CrossOrigin
//TODO: Need data sanitisation for input data.
public class ShortUrlController
{
    @Autowired
    private ShortUrlService urlService;


    /**
     * A helpful API endpoint to view all entries
     * in the DB. Not something you'd ship to prod.
     * @return
     */
    @GetMapping(path = "/allUrls")
    public ResponseEntity<?> getAllUrls()
    {
        final List<ShortUrl> urlList = urlService.getUrls();
        return ResponseEntity.ok(urlList);
    }

    /**
     * An API endpoint to create a short URL entry from a long URL
     * @param shortUrl   The shortUrl object which contains the long URL to convert
     * @return           A shortUrl which maps to the long URL in the DB
     */
    @PostMapping(path = "/create")
    public ResponseEntity<?> createShortUrl(@RequestBody ShortUrl shortUrl)
    {
        return ResponseEntity.ok(urlService.createShortUrl(shortUrl));
    }

    /**
     * An API endpoint which gets the long URL entry from a short URL
     * @param shortUrl The shortUrl object which contains the short URL to convert
     * @return         A longUrl mapped from the passed in shortUrl. Returns an
     *                 empty string if no long URL maps to this short URL.
     */
    @PostMapping(path = "/getLongFromShort")
    public ResponseEntity<?> getLongUrlFromShortUrl(@RequestBody ShortUrl shortUrl)
    {
        final String longUrl = urlService.getLongUrlByShort(shortUrl.getShortUrl());
        return longUrl == null ? ResponseEntity.ok("") : ResponseEntity.ok(longUrl);
    }
}
