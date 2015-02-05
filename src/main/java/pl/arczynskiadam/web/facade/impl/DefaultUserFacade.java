package pl.arczynskiadam.web.facade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.UserVO;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.facade.UserFacade;

@Component
public class DefaultUserFacade implements UserFacade {

	@Resource
	UserService userService;
	
	@Override
	public void saveNewUser(UserVO user) {
		userService.saveNewUser(user);
	}

	@Override
	public UserVO findUserById(int id) {
		return userService.findUserById(id);
	}
	
	@Override
	public AnonymousUserVO findAnonymousUserByNick(String nick) {
		return userService.findAnonymousUserByNick(nick);
	}
}
