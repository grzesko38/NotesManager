package pl.arczynskiadam.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pl.arczynskiadam.core.dao.AnonymousUserRepository;
import pl.arczynskiadam.core.dao.UserRepository;
import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.UserVO;
import pl.arczynskiadam.core.service.UserService;

@Service("userService")
public class DefaultUserService implements UserService {

	@Resource
	private UserRepository userDao;
	
	@Resource
	private AnonymousUserRepository anonymousDao;
	
	@Override
	public void saveNewUser(UserVO user) {
		userDao.save(user);
	}
	
	@Override
	public UserVO findUserById(int id) {
		return userDao.findOne(id);
	}
	
	@Override
	public UserVO findUserByNick(String nick) {
		return userDao.findUserByNick(nick);
	}
	
	@Override
	public AnonymousUserVO findAnonymousUserByNick(String nick) {
		return anonymousDao.findAnonymousUserByNick(nick);
	}
}
