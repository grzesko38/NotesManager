package pl.arczynskiadam.web.facade;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.RegisteredUserVO;

public interface UserFacade {
	public RegisteredUserVO getCurrentUser();
	public AnonymousUserVO findAnonymousUserByNick(String nick);
}
