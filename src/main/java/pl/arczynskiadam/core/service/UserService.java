package pl.arczynskiadam.core.service;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.UserVO;


public interface UserService {
	public void saveNewUser(UserVO user);
//	public void saveNewAnonymousUser(AnonymousUserVO anonymous);
	public UserVO findUserById(int id);
	public AnonymousUserVO findAnonymousUserByNick(String nick);
}
