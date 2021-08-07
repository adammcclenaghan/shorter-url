package com.example.accessingmongodbdatarest.service;

import com.example.accessingmongodbdatarest.dao.MongoShortUrlDao;
import com.example.accessingmongodbdatarest.model.ShortUrl;
import com.example.accessingmongodbdatarest.util.ShortenedUrlGenerator;
import com.example.accessingmongodbdatarest.util.base62.Base62ShortenedUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService
{
    @Autowired
    private MongoShortUrlDao mongoRepository;

    private ShortenedUrlGenerator generator = new Base62ShortenedUrlGenerator();

    public ShortUrlService() {};

    public List<ShortUrl> getUrls()
    {
        return mongoRepository.findAll();
    }

    //TODO: Tidy this when done.
    public ShortUrl createShortUrl(ShortUrl shortUrl)
    {
        ShortUrl existingEntry = mongoRepository.findByBaseUrl(shortUrl.getBaseUrl());
        if (existingEntry != null)
        {
            shortUrl.setShortUrl(existingEntry.getShortUrl());
        }
        else
        {
            // No entry exists for this base url so we create a new one.
            String proposedShortUrl = generator.getShortUrlFromLongUrl();
            // We could check here if the shortUrl already exists in the DB. We could continue
            // to try generating short URLs until we get one that doesn't clash.
            shortUrl.setShortUrl(proposedShortUrl);
            mongoRepository.save(shortUrl);
        }

        return shortUrl;
    }

    /**
     * Get the long URL associated with a shortUrl
     * @param shortUrl  The short URL to lookup in the db
     * @return          The long URL related to this short URL
     */
    public String getLongUrlByShort(final String shortUrl)
    {
        ShortUrl urlEntry = mongoRepository.findByShortUrl(shortUrl);
        // TODO: Exception handling when no entry for this long url exists.
        if (urlEntry != null)
        {
            return urlEntry.getBaseUrl();
        }
        else
        {
            //TODO: Throw here. Caller should catch and return something reasonable
            return null;
        }
    }
}
