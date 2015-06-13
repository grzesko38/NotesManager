package pl.arczynskiadam.web.controller.constants;

public interface GlobalControllerConstants {
	
	interface Misc {
		public static final String HASH = "#";
	}
	
	interface Prefixes {
		public static final String REDIRECT_PREFIX = "redirect:";
		public static final String FORWARD_PREFIX = "redirect:";
	}
	
	interface RequestParams {
		public static final String PAGE_NUMBER_PARAM		= "p";
		public static final String PAGE_SIZE_PARAM			= "size";
		public static final String SORT_COLUMN_PARAM 		= "col";
		public static final String ASCENDING_PARAM 			= "asc";
		public static final String ENTRIES_PER_PAGE_PARAM 	= "entries";
		public static final String DELETE_PARAM 			= "delete";
		public static final String CLEAR_DATE_FILTER_PARAM 	= "clrDateFilter";
	}
	
	interface ModelAttrKeys {
		interface Navigation {
			public static final String BREADCRUMBS_MODEL_ATTR = "breadcrumbs";
			
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
