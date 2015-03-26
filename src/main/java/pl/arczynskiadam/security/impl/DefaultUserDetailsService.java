package pl.arczynskiadam.security.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.arczynskiadam.core.model.UserRoleVO;
import pl.arczynskiadam.core.model.UserVO;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.SecurityConstants;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@Service(value = "userDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {

	@Autowired(required = true)
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserVO user = userService.findUserByNick(userName);
		
		if(user == null) {
	        throw new UsernameNotFoundException("User for username " + userName + "was not found.");
	    }
	 
	    List<String> permissions = FluentIterable.from(user.getUserRoles()).transform(toPermission()).toList();
	 
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
	
	private Function<UserRoleVO, String> toPermission() {
		return new Function<UserRoleVO, String>() {
			
			@Override
			public String apply(UserRoleVO arg0) {
				return arg0.getRole();
			}
		};
	}

//	public UserService getUserService() {
//		return userService;
//	}
//
//	@Required
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}
}
