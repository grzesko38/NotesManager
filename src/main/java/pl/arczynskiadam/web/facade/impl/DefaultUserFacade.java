package pl.arczynskiadam.web.facade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pl.arczynskiadam.core.dao.UserRepository;
import pl.arczynskiadam.core.model.UserVO;
import pl.arczynskiadam.web.facade.UserFacade;

@Component
public class DefaultUserFacade implements UserFacade {

	@Resource
	UserRepository userDao;
	
	@Override
	public void addUser(UserVO user) {
		userDao.save(user);
	}

	@Override
	public UserVO findUserById(int id) {
		return userDao.findOne(id);
	}
}
