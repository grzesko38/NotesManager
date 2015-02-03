package pl.arczynskiadam.web.facade;

import pl.arczynskiadam.core.model.UserVO;

public interface UserFacade {
	public void addUser(UserVO user);
	public UserVO findUserById(int id);
}
