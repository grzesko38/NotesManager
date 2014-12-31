package pl.arczynskiadam.web.controller;

public interface GlobalControllerConstants {
	
	interface Misc {
		public static final String HASH = "#";
	}
	
	interface Prefixes {
		public static final String REDIRECT = "redirect:";
	}
	
	interface RequestParams {
		public static final String PAGE 		= "p";
		public static final String PAGE_SIZE	= "size";
		public static final String SORT_COLUMN 	= "col";
		public static final String SORT_ORDER 	= "sort";
		public static final String ENTRIES 		= "entries";
	}
	
	interface ModelAttrKeys {
		interface Navigation {
			public static final String Breadcrumbs = "breadcrumbs";
			
		}
		interface Form {
			public static final String EntriesPerPage 		= "entriesPerPageForm";
			public static final String SelectedCheckboxes 	= "selectedCheckboxesForm";
		}
		interface Messages {
			public static final String ERROR_MSG 	= "errorMsgs";
			public static final String WARN_MSG		= "warnMsgs";
			public static final String INFO_MSG		= "infoMsgs"; 
		}
	}
	
	interface Defaults {
		interface Pagination {
			public static final int FIRST_PAGE = 0;
		}
	}
}
