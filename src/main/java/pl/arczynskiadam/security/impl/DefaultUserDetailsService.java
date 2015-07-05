package pl.arczynskiadam.security.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.model.UserRoleModel;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.SecurityConstants;

public class DefaultUserDetailsService implements UserDetailsService {

	UserService userService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		RegisteredUserModel user = userService.findRegisteredUserByNick(userName);
		
		if(user == null) {
	        throw new UsernameNotFoundException("User for username " + userName + "was not found.");
	    }
	 
	    List<String> permissions = user.getUserRoles().stream().map(UserRoleModel::getRole).collect(Collectors.toList());
	    
	    if(permissions.isEmpty()) {
	        throw new UsernameNotFoundException(userName + "has no permissions.");
	    }
	 
	    Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
	 
	    for(String permission : permissions) {
	        authorities.add(new SimpleGrantedAuthority(permission));
	    }
	 
	    String plain = user.getPasswordSalt().concat(SecurityConstants.DELIMITER).concat(user.getPasswordHash());
	    return new User(user.getNick(), plain, true, true, true, true, authorities);
	}

	public UserService getUserService() {
		return userService;
	}

	@Required
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
