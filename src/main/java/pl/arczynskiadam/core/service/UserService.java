package pl.arczynskiadam.core.service;

import pl.arczynskiadam.core.model.UserVO;


public interface UserService {
	public void addUser(UserVO user);
	public UserVO findUserById(int id);
}
