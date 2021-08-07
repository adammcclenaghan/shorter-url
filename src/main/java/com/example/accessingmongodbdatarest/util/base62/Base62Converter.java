package com.example.accessingmongodbdatarest.util.base62;

public class Base62Converter
{
    /* Possible characters of a base62 encoded string */
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int BASE_62 = CHARACTERS.length();

    /**
     * Encode a long in base 10 to a base62 encoded
     * string.
     * @param numBaseTen The number in base 10 to encode
     * @return           Base62 encoded string
     */
    public static String encode(long numBaseTen)
    {
        StringBuilder sb = new StringBuilder();
        while (numBaseTen > 0)
        {
            sb.append(CHARACTERS.charAt( (int) (numBaseTen % BASE_62 )));
            numBaseTen /= BASE_62;
        }

        return sb.reverse().toString();
    }
}
