package com.example.accessingmongodbdatarest.service;

import com.example.accessingmongodbdatarest.dao.MongoShortUrlDao;
import com.example.accessingmongodbdatarest.model.UrlDbEntry;
import com.example.accessingmongodbdatarest.util.ShortenedUrlGenerator;
import com.example.accessingmongodbdatarest.util.base62.Base62ShortenedUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ShortUrlService
{
    @Autowired
    private MongoShortUrlDao mongoRepository;
    private ShortenedUrlGenerator generator = new Base62ShortenedUrlGenerator();

    public ShortUrlService() {}

    /**
     * Get all of the URL db entries.
     * @return A list of all URL db entries.
     */
    public List<UrlDbEntry> getUrls()
    {
        return mongoRepository.findAll();
    }

    /**
     * Given a long URL, create a short URL entry in the DB and set the shortUrl value on
     * the passed in urlDbEntry.
     * @param longUrl                   The long URL to create a short URL entry for.
     * @return                          A representation of the created db entry.
     */
    public UrlDbEntry createShortUrl(final String longUrl)
    {
        if (longUrl == null || longUrl.isEmpty())
        {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Must supply a long URL value to shorten.");
        }

        // Representation of the DB entry which we will modify and return to the caller
        final UrlDbEntry entry = new UrlDbEntry();
        entry.setLongUrl(longUrl);

        final UrlDbEntry existingEntry = mongoRepository.findByLongUrl(longUrl);
        if (existingEntry != null)
        {
            // Do not create a new entry if one already exists in the DB for the provided long URL
            entry.setShortUrl(existingEntry.getShortUrl());
        }
        else
        {
            // No entry exists for this base url so create a new one.

            /*
              This isn't efficient but since our generator doesn't save its state between restarts
              (ie it doesn't save the last long value that it converted to a base62 shortUrl)
              we keep generating until we get a shortUrl value that does not already exist in the
              db. How long this takes will depend on the number of existing DB entries after a restart.
              In a prod env there's several ways we could change the generation to try to avoid this
              problem and be more efficient, but this is just a learning project...
             */
            String shortUrl;
            do
            {
                shortUrl = generator.generateShortUrl();
            }
            while((mongoRepository.findByShortUrl(shortUrl)) != null);

            entry.setShortUrl(shortUrl);
            mongoRepository.save(entry);
        }

        return entry;
    }

    /**
     * Get the long URL associated with a shortened URL.
     * @param shortUrl    The short URL to obtain a long URL for.
     * @return            A representation of the created db entry containing the long URL.
     *                    If no entry exists for the shortUrl then the returned db entry will
     *                    contain an empty string for the long URL value.
     */
    public UrlDbEntry getLongUrlByShort(final String shortUrl)
    {
        if (shortUrl == null || shortUrl.isEmpty())
        {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Must supply a short URL value.");
        }

        // Representation of the DB entry which we will modify and return to the caller
        final UrlDbEntry entry = new UrlDbEntry();
        entry.setShortUrl(shortUrl);

        final UrlDbEntry existingEntry = mongoRepository.findByShortUrl(shortUrl);
        if (existingEntry != null)
        {
            // An entry exists for this short URL
            entry.setLongUrl(existingEntry.getLongUrl());
        }
        else
        {
            // No entry in the DB for the provided short URL. An alternative here would be to throw an exception.
            entry.setLongUrl("");
        }

        return entry;
    }
}
