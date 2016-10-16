package com.razorthink.personalbrain.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.razorthink.personalbrain.auth.filter.AuthenticationFilter;
import com.razorthink.personalbrain.auth.token.EhCacheTokenServiceImpl;
import com.razorthink.personalbrain.auth.token.TokenService;
import com.razorthink.personalbrain.bean.OperationStatus;
import com.razorthink.personalbrain.bean.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dey on 6/3/16.
 */
@Component
public class LogOutHandler implements LogoutHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogOutHandler.class);
    private TokenService tokenService = new EhCacheTokenServiceImpl();

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication )
    {
        try
        {
            HttpServletRequest httpRequest = asHttp(request);
            Optional<String> token = Optional.fromNullable(httpRequest.getHeader(AuthenticationFilter.XAUTHTOKEN));
            tokenService.remove(token.get());
            SecurityContextHolder.clearContext();

            Response<String> resp = new Response<>();
            resp.setHttpStatus(HttpStatus.OK);
            resp.setHttpStatusCode(200);
            resp.setResult("success");
            resp.setStatus(OperationStatus.SUCCESS);

            String responseValue = new ObjectMapper().writeValueAsString(new Gson().toJson(resp));
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.addHeader("Content-Type", "application/json");
            response.getWriter().print(responseValue);
        }
        catch( Exception e )
        {
            LOGGER.error(e.getMessage(), e);
            String responseValue;
            try
            {
                responseValue = new ObjectMapper().writeValueAsString("failed");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.addHeader("Content-Type", "application/json");
                response.getWriter().print(responseValue);
            }
            catch( IOException e1 )
            {
                LOGGER.error(e1.getMessage(), e1);
            }
        }
    }

    private HttpServletRequest asHttp(ServletRequest request )
    {
        return (HttpServletRequest) request;
    }

}