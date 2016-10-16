package com.razorthink.personalbrain.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserPassAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -1510837420884856567L;

    public UserPassAuthenticationToken( Object principal, Object credentials )
    {
        super(principal, credentials);
    }

    public UserPassAuthenticationToken( Object principal, Object credentials, String companyName )
    {
        super(principal, credentials);
    }

    public UserPassAuthenticationToken( Object aPrincipal, Object aCredentials,
            Collection<? extends GrantedAuthority> anAuthorities )
    {
        super(aPrincipal, aCredentials, anAuthorities);
    }
}
