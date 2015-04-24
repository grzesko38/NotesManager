package pl.arczynskiadam.web.facade;

import pl.arczynskiadam.core.model.AnonymousUserModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;

public interface UserFacade {
	public RegisteredUserModel getCurrentUser();
	public AnonymousUserModel findAnonymousUserByNick(String nick);
}
