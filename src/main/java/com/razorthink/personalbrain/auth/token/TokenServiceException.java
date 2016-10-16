/**
 *
 */
package com.razorthink.personalbrain.auth.token;


public class TokenServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public TokenServiceException( String message )
    {
        super(message);
    }
}
