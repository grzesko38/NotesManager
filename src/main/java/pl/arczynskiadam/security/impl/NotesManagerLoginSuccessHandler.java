package pl.arczynskiadam.security.impl;

import static pl.arczynskiadam.core.service.constants.ServiceConstants.Session.Attributes.NOTES_PAGINATION;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class NotesManagerLoginSuccessHandler implements AuthenticationSuccessHandler {

	protected String defaultTargetUrl;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	request.getSession().setAttribute(NOTES_PAGINATION, null);
    	response.sendRedirect(request.getContextPath() + defaultTargetUrl);
    }

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
}