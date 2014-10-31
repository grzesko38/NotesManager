package pl.arczynskiadam.web.controller;

public interface GlobalControllerConstants {
	
	interface Misc {
		public static final String hash = "#";
	}
	
	interface Prefixes {
		public static final String redirect = "redirect:";
	}
	
	interface RequestParams {
		public static final String PAGE 						= "p";
		public static final String RESTORE_PAGE_FROM_SESSION 	= "p_restore";
		public static final String SORT_COLUMN 	= "col";
		public static final String SORT_ORDER 	= "sort";
		public static final String ENTRIES = "entries";
	}
	
	interface ModelAttrKeys {
		interface Navigation {
			public static final String breadcrumbs = "breadcrumbs";
			
		}
		interface Form {
			public static final String entriesPerPageForm 		= "entriesPerPageForm";
			public static final String selectedCheckboxesForm 	= "selectedCheckboxesForm";
		}
	}
	
	interface Defaults {
		public static final int FIRST_PAGE = 0;
	}
}
