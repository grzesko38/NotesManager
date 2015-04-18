package pl.arczynskiadam.core.service;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.RegisteredUserVO;

public interface UserService {
	public RegisteredUserVO findRegisteredUserByNick(String nick);
	public AnonymousUserVO findAnonymousUserByNick(String nick);
	public RegisteredUserVO getCurrentUser();
}
