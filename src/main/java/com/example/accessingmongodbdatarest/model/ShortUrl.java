package com.example.accessingmongodbdatarest.model;

import org.springframework.data.annotation.Id;

//TODO: The name 'ShortUrl' is confusing when there is a member variable with the same name.
public class ShortUrl
{
    public String getBaseUrl()
    {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
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
    private String baseUrl;
    private String shortUrl;
}
