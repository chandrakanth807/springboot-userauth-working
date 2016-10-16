package com.razorthink.personalbrain.auth.token;

import org.springframework.security.core.Authentication;


public interface TokenService {

    String generateNewToken();

    void store(String token, Authentication authentication) throws TokenServiceException;

    boolean contains(String token);

    Authentication retrieve(String token) throws TokenServiceException;

    boolean remove(String token);
}
