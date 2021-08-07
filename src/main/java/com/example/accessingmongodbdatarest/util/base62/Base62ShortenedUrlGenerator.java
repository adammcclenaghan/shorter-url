package com.example.accessingmongodbdatarest.util.base62;

import com.example.accessingmongodbdatarest.util.ShortenedUrlGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class Base62ShortenedUrlGenerator implements ShortenedUrlGenerator
{
    //TODO: Unit tests

    private AtomicLong counter = new AtomicLong(1);

    @Override
    public String getShortUrlFromLongUrl()
    {
        final long longVal = counter.getAndIncrement();
        return Base62Converter.
                encode(longVal);
    }

    /*
    For dev testing only.
     */
    public static void main(String[] args)
    {
        Base62ShortenedUrlGenerator generator = new Base62ShortenedUrlGenerator();
        for(int i = 0; i < 20; i++ )
        {
            System.out.println(generator.getShortUrlFromLongUrl());
        }
    }
}
