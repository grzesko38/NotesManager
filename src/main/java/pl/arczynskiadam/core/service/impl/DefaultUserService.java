package pl.arczynskiadam.core.service.impl;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pl.arczynskiadam.core.dao.NoteRepository;
import pl.arczynskiadam.core.dao.RegisteredUserRepository;
import pl.arczynskiadam.core.dao.UserRepository;
import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.RegisteredUserVO;
import pl.arczynskiadam.core.service.UserService;

@Service("userService")
public class DefaultUserService implements UserService {

	@Resource
	private UserRepository userDao;
	
	@Resource
	private NoteRepository notesDao;
	
	@Override
	public RegisteredUserVO findRegisteredUserByNick(String nick) {
		return userDao.findRegisteredUserByNick(nick);
	}
	
	@Override
	public AnonymousUserVO findAnonymousUserByNick(String nick) {
		return userDao.findAnonymousdUserByNick(nick);
	}
	
	@Override
	public RegisteredUserVO getCurrentUser() {
	    SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();
	        return principal instanceof UserDetails ? findRegisteredUserByNick(((UserDetails)principal).getUsername()) : null;
	    }
	    return null;
	}
}
