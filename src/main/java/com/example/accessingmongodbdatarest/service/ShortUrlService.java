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

            /*
              This isn't  efficient but since our generator doesn't save its state between restarts
              (ie it doesn't save the last long value that it converted to a base62 shortUrl)
              we keep generating until we get a shortUrl value that does not already exist in the
              db. How long this takes will depend on the number of existing DB entries after a restart.
              In a real world app there's several ways we could change the generation to try to avoid this
              problem and be more efficient, but this is just a learning project...
             */
            String proposedShortUrl;
            do
            {
                proposedShortUrl = generator.getShortUrlFromLongUrl();
            }
            while((mongoRepository.findByShortUrl(proposedShortUrl)) != null);

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
