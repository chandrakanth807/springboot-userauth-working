package com.razorthink.personalbrain.controller;

import com.razorthink.personalbrain.auth.bean.User;
import com.razorthink.personalbrain.constants.Constant;
import com.razorthink.personalbrain.exceptions.WebappException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

public class AbstractWebappController extends AbstractController {

	protected User getCurrentUser() throws WebappException
	{
		try
		{
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
			if( user == null )
			{
				throw new WebappException(Constant.Webapp.ERROR_FETCHING_CURRENT_USER, HttpStatus.FORBIDDEN);
			}
			return user;
		}
		catch( WebappException we )
		{
			throw we;
		}
		catch( Exception e )
		{
			throw new WebappException(e);
		}

	}

}
