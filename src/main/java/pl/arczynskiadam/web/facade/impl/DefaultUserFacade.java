package pl.arczynskiadam.web.facade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pl.arczynskiadam.core.model.AnonymousUserModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.facade.UserFacade;

@Component
public class DefaultUserFacade implements UserFacade {

	@Resource
	UserService userService;
	
	@Override
	public RegisteredUserModel getCurrentUser() {
		return userService.getCurrentUser();
	}
	
	@Override
	public AnonymousUserModel findAnonymousUserByNick(String nick) {
		return userService.findAnonymousUserByNick(nick);
	}
}
