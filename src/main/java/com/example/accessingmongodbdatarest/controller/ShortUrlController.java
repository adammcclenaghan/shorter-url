package com.example.accessingmongodbdatarest.controller;

import com.example.accessingmongodbdatarest.model.ShortUrl;
import com.example.accessingmongodbdatarest.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RepositoryRestController
@RequestMapping("/shortUrl/")
@CrossOrigin
public class ShortUrlController
{
    @Autowired
    private ShortUrlService urlService;

    @GetMapping(path = "/allUrls")
    public ResponseEntity<?> getAllUrls()
    {
        final List<ShortUrl> urlList = urlService.getUrls();
        return ResponseEntity.ok(urlList);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createShortUrl(@RequestBody ShortUrl shortUrl)
    {
        return ResponseEntity.ok(urlService.createShortUrl(shortUrl));
    }

    /**
     * Search db and redirect requests to the correct long URL
     */
    @RequestMapping("/")
    @GetMapping(value="**")
    public ResponseEntity<?> requestShortUrl() {
        // Lookup the shortUrl string in the db
        // Get the long url
        // Return redirect to the long url
        return ResponseEntity.ok("Capture all");
    }
}
