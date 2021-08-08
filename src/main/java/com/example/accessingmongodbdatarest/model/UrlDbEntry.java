package com.example.accessingmongodbdatarest.model;

import org.springframework.data.annotation.Id;

/**
 * The DB entry which maps the full length long URL to a shortened
 * URL.
 */
public class UrlDbEntry
{
    public String getLongUrl()
    {
        return longUrl;
    }

    public void setLongUrl(String longUrl)
    {
        this.longUrl = longUrl;
    }

    public String getShortUrl()
    {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl)
    {
        this.shortUrl = shortUrl;
    }

    @Id
    private String id;
    private String longUrl;
    private String shortUrl;
}
