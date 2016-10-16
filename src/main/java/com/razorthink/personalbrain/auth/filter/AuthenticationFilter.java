package com.razorthink.personalbrain.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorthink.personalbrain.auth.token.APIAuthPaths;
import com.razorthink.personalbrain.auth.token.UserPassAuthenticationToken;
import com.razorthink.personalbrain.bean.OperationStatus;
import com.razorthink.personalbrain.bean.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String XAUTHUSERNAME = "X-Auth-Username";
    private static final String XAUTHPASSWORD = "X-Auth-Password";
    public static final String XAUTHTOKEN = "X-Auth-Token";
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter( AuthenticationManager authenticationManager )
    {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
            throws ServletException, IOException
    {

        Optional<String> username = Optional.ofNullable(request.getHeader(XAUTHUSERNAME));
        Optional<String> password = Optional.ofNullable(request.getHeader(XAUTHPASSWORD));
        Optional<String> token = Optional.ofNullable(request.getHeader(XAUTHTOKEN));

        String resourcePath = new UrlPathHelper().getPathWithinApplication(request);

        try
        {
            if( postToAuthenticate(request, resourcePath) )
            {
                logger.info("Trying to authenticate user {} by X-Auth-Username method", username);
                processUsernamePasswordAuthentication(request, response, username, password);
                return;
            }

            if( token.isPresent() )
            {
                logger.info("Trying to authenticate user by X-Auth-Token method. Token: {}", token);
                processTokenAuthentication(token);
            }
            filterChain.doFilter(request, response);

        }
        catch( InternalAuthenticationServiceException e )
        {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        catch( AuthenticationException e )
        {
            logger.error("exception", e);
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    private boolean postToAuthenticate(HttpServletRequest httpRequest, String resourcePath )
    {
        return APIAuthPaths.AUTH_INTERNAL.equalsIgnoreCase(resourcePath)
                && (httpRequest.getMethod().equals("POST") || httpRequest.getMethod().equals("OPTIONS"));
    }

    private void processUsernamePasswordAuthentication(HttpServletRequest request, HttpServletResponse httpResponse,
                                                       Optional<String> username, Optional<String> password ) throws IOException
    {
        Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username, password);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        Response response = new Response<>(resultOfAuthentication.getDetails());
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpStatusCode(200);
        response.setStatus(OperationStatus.SUCCESS);

        String jsonResponse = new ObjectMapper().writeValueAsString(response);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(jsonResponse);
    }

    private Authentication tryToAuthenticateWithUsernameAndPassword(Optional<String> username,
                                                                    Optional<String> password )
    {
        UserPassAuthenticationToken requestAuthentication = new UserPassAuthenticationToken(username, password);
        return tryToAuthenticate(requestAuthentication);
    }

    private void processTokenAuthentication( Optional<String> token )
    {
        Authentication resultOfAuthentication = tryToAuthenticateWithToken(token);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryToAuthenticateWithToken(Optional<String> token )
    {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token,
                null);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication )
    {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if( responseAuthentication == null || !responseAuthentication.isAuthenticated() )
        {
            throw new InternalAuthenticationServiceException(
                    "Unable to authenticate Domain User for provided credentials");
        }
        logger.info("User successfully authenticated");
        return responseAuthentication;
    }
}