package pl.arczynskiadam.web.facade.impl;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pl.arczynskiadam.core.model.AnonymousUserModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.model.UserRoleModel;
import pl.arczynskiadam.core.service.UserPreferencesService;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.security.impl.EncodingHelper;
import pl.arczynskiadam.security.impl.SHA256SaltedPasswordEncoder;
import pl.arczynskiadam.security.impl.SaltGenerator;
import pl.arczynskiadam.web.SecurityConstants;
import pl.arczynskiadam.web.facade.UserFacade;

@Component
public class DefaultUserFacade implements UserFacade {

	@Resource(name = "userService")
	UserService userService;
	
	@Resource(name = "userPreferencesService")
	UserPreferencesService userPreferencesService;
	
	@Resource(name = "passwordEncoder")
	SHA256SaltedPasswordEncoder sha256Encoder;
	
	@Override
	public RegisteredUserModel getCurrentUser() {
		return userService.getCurrentUser();
	}
	
	@Override
	public AnonymousUserModel findAnonymousUserByNick(String nick) {
		return userService.findAnonymousUserByNick(nick);
	}
	
	@Override
	public boolean isCurrentUserAnonymous() {
		return userService.isCurrentUserAnonymous();
	}
	
	@Override
	public void registerUser(String nick, String email, String plainPassword) {
		RegisteredUserModel newUser = new RegisteredUserModel();
		newUser.setNick(nick);
		newUser.setEmail(email);
		newUser.setDateRegistered(LocalDateTime.now());
		newUser.setEnabled(true);
		newUser.setPasswordSalt(SaltGenerator.generateRandomSalt(16));
		newUser.setPasswordEncoding(SecurityConstants.DEFAULT_ENCODING);
		newUser.setPasswordHash(sha256Encoder.encode(EncodingHelper.buildPlainText(plainPassword, newUser.getPasswordSalt())));
		newUser.setEnabled(true);
		
		UserRoleModel role = new UserRoleModel();
		role.setRole("ROLE_USER");
		newUser.addUserRole(role);
		
		newUser.setUserPreferences(userPreferencesService.buildDefaultUserPreferences());
		
		userService.registerUser(newUser);
	}
}
