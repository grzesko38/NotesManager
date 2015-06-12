package pl.arczynskiadam.security.impl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class AuthFilter extends AbstractAuthenticationProcessingFilter {

	  private static final String DEFAULT_FILTER_PROCESSES_URL = "/login";
	  private static final String POST = "POST";
	  
	  private String usernameParameter = "j_username";
		private String passwordParameter = "j_password";
		
		private boolean postOnly = true;

	  public AuthFilter () {
	    super(DEFAULT_FILTER_PROCESSES_URL);
	  }

	  public Authentication attemptAuthentication(HttpServletRequest request,
				HttpServletResponse response) throws AuthenticationException {
			if ((this.postOnly) && (!(request.getMethod().equals("POST")))) {
				throw new AuthenticationServiceException(
						"Authentication method not supported: "
								+ request.getMethod());
			}

			String username = obtainUsername(request);
			String password = obtainPassword(request);

			if (username == null) {
				username = "";
			}

			if (password == null) {
				password = "";
			}

			username = username.trim();

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					username, password);

			setDetails(request, authRequest);

			return getAuthenticationManager().authenticate(authRequest);
		}
	  
	  protected String obtainPassword(HttpServletRequest request) {
			return request.getParameter(this.passwordParameter);
		}

		protected String obtainUsername(HttpServletRequest request) {
			return request.getParameter(this.usernameParameter);
		}
		
		protected void setDetails(HttpServletRequest request,
				UsernamePasswordAuthenticationToken authRequest) {
			authRequest.setDetails(this.authenticationDetailsSource
					.buildDetails(request));
		}
	  
	  @Override
	  public void doFilter(ServletRequest req, ServletResponse res,
	    FilterChain chain) throws IOException, ServletException {
	    final HttpServletRequest request = (HttpServletRequest) req;
	    final HttpServletResponse response = (HttpServletResponse) res;
	    if(request.getMethod().equals(POST)) {
	    	request.getSession().setAttribute("notesPaginationData", null);
	      super.doFilter(request, response, chain);
	    } else {
	      // If it's a GET, we ignore this request and send it
	      // to the next filter in the chain.  In this case, that
	      // pretty much means the request will hit the /login
	      // controller which will process the request to show the
	      // login page.
	      chain.doFilter(request, response);
	    }
	  }



	}