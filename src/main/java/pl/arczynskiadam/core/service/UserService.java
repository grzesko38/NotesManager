package pl.arczynskiadam.core.service;

import pl.arczynskiadam.core.model.AnonymousUserModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;

public interface UserService {
	public RegisteredUserModel findRegisteredUserByNick(String nick);
	public AnonymousUserModel findAnonymousUserByNick(String nick);
	public RegisteredUserModel getCurrentUser();
	public boolean isCurrentUserAnonymous();
	public void registerUser(RegisteredUserModel user);
	public boolean isNickAvailable(String nick);
	public boolean isEmailAvailable(String email);
}
