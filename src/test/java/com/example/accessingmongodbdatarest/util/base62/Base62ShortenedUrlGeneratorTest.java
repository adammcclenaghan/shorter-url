package com.example.accessingmongodbdatarest.util.base62;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link Base62ShortenedUrlGenerator}
 */
class Base62ShortenedUrlGeneratorTest
{
    @BeforeEach
    public void setUp()
    {
        generator = new Base62ShortenedUrlGenerator();
    }

    @Test
    public void shouldGenerateBase62ValuesIncrementally()
    {
        for (int i = 0; i < Base62Converter.CHARACTERS.length(); i++)
        {
            assertEquals(String.valueOf(Base62Converter.CHARACTERS.charAt(i)),
                    generator.generateShortUrl(),
                    "Expected base62 values to generate incrementally");
        }

        assertEquals(generator.generateShortUrl(), "ba");
    }

    private Base62ShortenedUrlGenerator generator;
}