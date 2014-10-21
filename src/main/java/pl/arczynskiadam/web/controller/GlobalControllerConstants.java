package pl.arczynskiadam.web.controller;

public interface GlobalControllerConstants {
	
	interface Misc {
		public static final String hash = "#";
	}
	
	interface Prefixes {
		public static final String redirect = "redirect:";
	}
	
	interface RequestParams {
		public static final String ENTRIES = "entries";
	}
	
	interface ModelAttrKeys {
		interface Navigation {
			public static final String navItems = "navItems";
			
		}
		interface Form {
			public static final String entriesPerPage 	= "entriesPerPageForm";
		}
	}
}
