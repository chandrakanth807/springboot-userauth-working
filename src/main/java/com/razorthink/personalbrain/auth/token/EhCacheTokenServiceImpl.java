package com.razorthink.personalbrain.auth.token;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;

import java.util.UUID;


public class EhCacheTokenServiceImpl implements TokenService {

    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private Cache cache = CacheManager.getInstance().getCache("restApiAuthTokenCache");

    @Scheduled( fixedRate = HALF_AN_HOUR_IN_MILLISECONDS )
    public void evictExpiredTokens()
    {
        logger.info("Evicting expired tokens");
        cache.evictExpiredElements();
    }

    public String generateNewToken()
    {
        return UUID.randomUUID().toString();
    }

    public void store( String token, Authentication authentication )
    {
        cache.put(new Element(token, authentication));
    }

    public boolean contains( String token )
    {
        return cache.get(token) != null;
    }

    public Authentication retrieve(String token )
    {
        return (Authentication) cache.get(token).getObjectValue();
    }

    public boolean remove( String token )
    {
        return cache.remove(token);
    }

    public Results queryForUsername(String uname, String pwd )
    {
        Attribute<String> userName = cache.getSearchAttribute("userName");
        Attribute<String> password = cache.getSearchAttribute("password");

        return cache.createQuery().addCriteria(userName.eq(uname)).addCriteria(password.eq(pwd)).includeKeys()
                .includeValues().execute();

    }
}