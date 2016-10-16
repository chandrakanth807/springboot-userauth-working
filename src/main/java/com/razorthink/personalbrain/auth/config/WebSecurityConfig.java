package com.razorthink.personalbrain.auth.config;

import com.razorthink.personalbrain.auth.filter.AuthenticationFilter;
import com.razorthink.personalbrain.auth.handler.LogOutHandler;
import com.razorthink.personalbrain.auth.provider.TokenAuthenticationProvider;
import com.razorthink.personalbrain.auth.provider.UsernamePasswordAuthProvider;
import com.razorthink.personalbrain.auth.token.EhCacheTokenServiceImpl;
import com.razorthink.personalbrain.auth.token.HttpAuthenticationEntryPoint;
import com.razorthink.personalbrain.exceptions.WebappException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
@Order( SecurityProperties.ACCESS_OVERRIDE_ORDER )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private LogOutHandler logoutHandler;
    private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    protected void configure( HttpSecurity http ) throws WebappException
    {

        try
        {
            logger.info("WebSecurity Configure..");
            http.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and().headers()
                    .cacheControl().and()
                    .addHeaderWriter(
                            new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                    .and().authorizeRequests()

                    // Allow anonymous resource requests
                    .antMatchers("/").permitAll().antMatchers("/login").permitAll().antMatchers("/pages/**").permitAll()
                    // Allow anonymous logins
                    .antMatchers("/auth/**").permitAll()

                    .antMatchers("/error/**").permitAll()
                    // All other request need to be authenticated
                    .antMatchers("/rest/**").authenticated().and()

                    // Custom Token based authentication based on the header
                    // previously given to the client..
                    .addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)

                    .logout().addLogoutHandler(logoutHandler).invalidateHttpSession(true)
                    .logoutUrl("/rest/auth/logout");
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).sessionFixation()
                    .changeSessionId();
        }
        catch( Exception e )
        {
            logger.error("Error occurred in WebSecurityConfig.configure: " + e);
            throw new WebappException(e.getMessage(), HttpStatus.UNAUTHORIZED, e);
        }
    }

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.authenticationProvider(domainUsernamePasswordAuthenticationProvider())
                .authenticationProvider(tokenAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider domainUsernamePasswordAuthenticationProvider()
    {
        return new UsernamePasswordAuthProvider(new EhCacheTokenServiceImpl());
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider()
    {
        return new TokenAuthenticationProvider(new EhCacheTokenServiceImpl());
    }
}
