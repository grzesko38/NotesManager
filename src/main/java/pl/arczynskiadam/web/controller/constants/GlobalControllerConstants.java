package pl.arczynskiadam.web.controller.constants;

public interface GlobalControllerConstants {
	
	interface Misc {
		public static final String HASH = "#";
	}
	
	interface Prefixes {
		public static final String REDIRECT = "redirect:";
	}
	
	interface RequestParams {
		public static final String PAGE_NUMBER	= "p";
		public static final String PAGE_SIZE	= "size";
		public static final String SORT_COLUMN 	= "col";
		public static final String ASCENDING 	= "asc";
		public static final String ENTRIES 		= "entries";
		public static final String DELETE 		= "delete";
	}
	
	interface ModelAttrKeys {
		interface Navigation {
			public static final String BREADCRUMBS = "breadcrumbs";
			
		}
		interface Form {
			public static final String SELECTED_CHECKBOXES_FORM = "selectedCheckboxesForm";
		}
		interface Messages {
			public static final String ERR_MSG 	= "errorMsgs";
			public static final String WARN_MSG	= "warningMsgs";
			public static final String INFO_MSG	= "infoMsgs"; 
		}
	}
}
