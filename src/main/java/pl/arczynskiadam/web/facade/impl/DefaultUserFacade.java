package pl.arczynskiadam.web.facade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.RegisteredUserVO;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.facade.UserFacade;

@Component
public class DefaultUserFacade implements UserFacade {

	@Resource
	UserService userService;
	
	@Override
	public RegisteredUserVO getCurrentUser() {
		return userService.getCurrentUser();
	}
	
	@Override
	public AnonymousUserVO findAnonymousUserByNick(String nick) {
		return userService.findAnonymousUserByNick(nick);
	}
}
