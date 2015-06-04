package pl.arczynskiadam.security.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class NotesManagerSuccessHandler implements AuthenticationSuccessHandler {

	protected String defaultTargetUrl;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	request.getSession().setAttribute("notesPaginationData", null);
    	response.sendRedirect(request.getContextPath() + defaultTargetUrl);
    }

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
}