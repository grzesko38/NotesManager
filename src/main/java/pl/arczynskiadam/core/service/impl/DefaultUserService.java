package pl.arczynskiadam.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pl.arczynskiadam.core.dao.UserRepository;
import pl.arczynskiadam.core.model.UserVO;
import pl.arczynskiadam.core.service.UserService;

@Service
public class DefaultUserService implements UserService {

	@Resource
	private UserRepository userDao;
	
	@Override
	public void addUser(UserVO user) {
		userDao.save(user);
	}
	
	@Override
	public UserVO findUserById(int id) {
		return userDao.findOne(id);
	}
}
