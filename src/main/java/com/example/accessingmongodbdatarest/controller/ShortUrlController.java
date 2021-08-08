package com.example.accessingmongodbdatarest.controller;

import com.example.accessingmongodbdatarest.model.UrlDbEntry;
import com.example.accessingmongodbdatarest.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RepositoryRestController
@RequestMapping("/shortUrl")
@CrossOrigin
public class ShortUrlController
{
    @Autowired
    private ShortUrlService urlService;

    /**
     * A helpful API endpoint to view all entries in the DB.
     * Not something you'd ship to prod.
     * @return
     */
    @GetMapping(path = "/allUrls")
    public ResponseEntity<?> getAllUrls()
    {
        final List<UrlDbEntry> urlList = urlService.getUrls();
        return ResponseEntity.ok(urlList);
    }

    /**
     * An API endpoint to create a short URL from a long URL
     * @param urlDbEntry   The db object which contains the long URL to convert
     * @return             A representation of the added DB object. Allows callers
     *                     to determine whether the call was successful and retrieve
     *                     the shortUrl which maps to the provided longUrl
     */
    @PostMapping(path = "/create")
    public ResponseEntity<?> createShortUrl(@RequestBody UrlDbEntry urlDbEntry)
    {
        final UrlDbEntry entry = urlService.createShortUrl(urlDbEntry.getLongUrl());
        return ResponseEntity.ok(entry);
    }

    /**
     * An API endpoint which gets the long URL entry based on a short URL
     * @param urlDbEntry The db object which contains the short URL to convert
     * @return         A longUrl mapped from the passed in shortUrl. Returns an
     *                 empty string if no long URL maps to this short URL.
     */
    @PostMapping(path = "/getLongFromShort")
    public ResponseEntity<?> getLongUrlFromShortUrl(@RequestBody UrlDbEntry urlDbEntry)
    {
        final UrlDbEntry entry = urlService.getLongUrlByShort(urlDbEntry.getShortUrl());
        return ResponseEntity.ok(entry);
    }
}
