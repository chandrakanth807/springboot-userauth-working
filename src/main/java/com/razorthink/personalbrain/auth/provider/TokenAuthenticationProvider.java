package com.razorthink.personalbrain.auth.provider;

import com.razorthink.personalbrain.auth.token.TokenService;
import com.razorthink.personalbrain.auth.token.TokenServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Optional;

/**
 * Created by dey on 6/3/16.
 */
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    @Autowired
    public TokenAuthenticationProvider( TokenService tokenService )
    {
        this.tokenService = tokenService;
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    @Override
    public Authentication authenticate(Authentication authentication ) throws AuthenticationException
    {
        Optional<String> token = (Optional) authentication.getPrincipal();
        if( !token.isPresent() || token.get().isEmpty() )
        {
            throw new BadCredentialsException("Invalid token");
        }
        if( !tokenService.contains(token.get()) )
        {
            logger.error("Invalid token or token expired");
            throw new BadCredentialsException("Invalid token or token expired");
        }
        try
        {
            return tokenService.retrieve(token.get());
        }
        catch( TokenServiceException e )
        {
            logger.error("Invalid token or token expired");
            logger.error(e.getMessage(), e);
            throw new BadCredentialsException("Invalid token or token expired");
        }
    }

    @Override
    public boolean supports( Class<?> authentication )
    {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
