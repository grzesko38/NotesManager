package pl.arczynskiadam.web.controller.constants;

public interface LoginControllerConstants {

	interface Pages {
		public static final String LOGIN_PAGE = "login";
	}
	
	interface ModelAttrKeys {
		interface Form {
			public static final String LOGIN_FORM = "loginForm";
		}
	}
	
	interface URLs {		
		public static final String LOGIN  = "/login";
		public static final String LOGOUT = "/logout";
	}
}
