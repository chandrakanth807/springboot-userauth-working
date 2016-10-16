package com.razorthink.personalbrain.auth.provider;

import com.razorthink.personalbrain.auth.UserAccessDetailsToken;
import com.razorthink.personalbrain.auth.bean.User;
import com.razorthink.personalbrain.auth.token.TokenService;
import com.razorthink.personalbrain.auth.token.UserPassAuthenticationToken;
import com.razorthink.personalbrain.entity.user.Users;
import com.razorthink.personalbrain.exceptions.WebappException;
import com.razorthink.personalbrain.repositories.user.UsersRepository;
import com.razorthink.personalbrain.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

/**
 * Created by dey on 6/3/16.
 */
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(UsernamePasswordAuthProvider.class);

    private TokenService tokenService;

    @Autowired
    private UsersRepository usersRepository;

    /**
     * constructor accepts @{@link TokenService}
     * 
     * @param tokenService
     *            is the tokenService interface
     */

    public UsernamePasswordAuthProvider( TokenService tokenService )
    {
        this.tokenService = tokenService;
    }

    @Override
    public boolean supports( Class<? extends Object> authentication )
    {
        return authentication.equals(UserPassAuthenticationToken.class);
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    @Override
    public Authentication authenticate(Authentication authentication )

    {

        LOG.debug("Authenticating device and user - assigning authorities...");

        validate(authentication);

        Optional<String> username = (Optional) authentication.getPrincipal();
        Optional<String> password = (Optional) authentication.getCredentials();

        User user = new User();
        user.setName(username.get());
        user.setPassword(password.get());

        try
        {
            List<Users> userList = usersRepository.findByUserName(user.getName().trim());
            if( userList == null || userList.isEmpty()
                    || !EncryptionUtil.getMD5(user.getPassword()).equals(userList.get(0).getUserPassword()) )

            {
                throw new WebappException("User not found");
            }

            Users u = userList.get(0);
            UserAccessDetailsToken accessDetailsToken = new UserAccessDetailsToken(username, null);
            accessDetailsToken.setUserId(Long.valueOf(u.getUserId()));
            String newToken = tokenService.generateNewToken();
            accessDetailsToken.setToken(newToken);
            user.setId(u.getUserId());
            user.setPassword("");
            user.setEmail(u.getUserEmail());
            user.setToken(newToken);
            accessDetailsToken.setDetails(user);
            tokenService.store(newToken, accessDetailsToken);
            accessDetailsToken.setAuthenticated(true);
            return accessDetailsToken;
        }
        catch( Exception cge )
        {
            LOG.error(cge.getMessage(), cge);
            throw new BadCredentialsException(cge.getMessage());
        }
    }

    private void validate( Authentication authentication )
    {
        if( authentication.getPrincipal() == null || authentication.getCredentials() == null )
        {
            LOG.error("Missing username or password");
            throw new BadCredentialsException("pmo.error.user.invalid.credentials");
        }

        Optional<String> username = (Optional) authentication.getPrincipal();
        Optional<String> password = (Optional) authentication.getCredentials();

        if( !username.isPresent() || !password.isPresent() || username.get().isEmpty() || password.get().isEmpty() )
        {
            LOG.error("Missing username or password");
            throw new BadCredentialsException("pmo.error.user.invalid.credentials");
        }
    }

    public void setUsersRepository( UsersRepository usersRepository )
    {
        this.usersRepository = usersRepository;
    }

}
