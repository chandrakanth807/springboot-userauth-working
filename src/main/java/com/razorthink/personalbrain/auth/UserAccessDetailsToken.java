package com.razorthink.personalbrain.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;


public class UserAccessDetailsToken extends PreAuthenticatedAuthenticationToken {

    private static final long serialVersionUID = 1L;
    private Long userId;

    public UserAccessDetailsToken( Object aPrincipal, Object aCredentials )
    {
        super(aPrincipal, aCredentials);
    }

    public UserAccessDetailsToken( Object aPrincipal, Object aCredentials,
            Collection<? extends GrantedAuthority> anAuthorities )
    {
        super(aPrincipal, aCredentials, anAuthorities);
    }

    public String getToken()
    {
        return (String) getDetails();
    }

    public void setToken( String token )
    {
        setDetails(token);
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId( Long userId )
    {
        this.userId = userId;
    }
}