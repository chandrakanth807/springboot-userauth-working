package com.razorthink.personalbrain.utils;


import com.razorthink.personalbrain.exceptions.WebappException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Contains method(s) that hash to MD5
 */
public final class EncryptionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtil.class);

    private EncryptionUtil()
    {
    }

    /**
     * method will hash the given string using md5 algorithm.
     *
     * @param input
     *            input string that need to be hashed
     * @return returns the hashed string
     */
    public static String getMD5( String input ) throws WebappException
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            int numOfZeroToBePrepended = 32 - hashtext.length();
            StringBuilder prependText = new StringBuilder();
            while( numOfZeroToBePrepended > 0 )
            {
                prependText.append("0");
                numOfZeroToBePrepended--;
            }

            hashtext = prependText.toString() + hashtext;
            return hashtext;
        }
        catch( NoSuchAlgorithmException e )
        {
            LOGGER.error(e.getMessage(), e);
            throw new WebappException(e, "Unable to find algorithm MD5");
        }
    }

    /**
     * method will encode the given string to base 64
     * 
     * @param rawString
     *            is the string to be encoded
     * @return encoded string
     * @throws WebappException
     *             if any error while encoding
     */
    public static String encodeToBase64( String rawString ) throws WebappException
    {
        if( rawString == null )
            throw new WebappException("Encrypting string cannot be null");

        return Base64.getEncoder().encodeToString(rawString.getBytes(StandardCharsets.UTF_8));

    }

    /**
     * method will decode the given encoded string
     * 
     * @param encodedString
     *            is the string to be decoded
     * @return decoded string
     * @throws WebappException
     *             if any error while decoding
     */
    public static String decodeBase64( String encodedString ) throws WebappException
    {

        if( encodedString == null )
            throw new WebappException("Encoded string cannot be null");

        return new String(Base64.getDecoder().decode(encodedString), StandardCharsets.UTF_8);

    }

}
