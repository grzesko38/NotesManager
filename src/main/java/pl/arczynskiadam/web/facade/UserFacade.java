package pl.arczynskiadam.web.facade;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.UserVO;

public interface UserFacade {
	public void saveNewUser(UserVO user);
	public UserVO findUserById(int id);
	public UserVO getCurrentUser();
	public AnonymousUserVO findAnonymousUserByNick(String nick);
}
