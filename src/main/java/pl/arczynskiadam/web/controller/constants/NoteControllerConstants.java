package pl.arczynskiadam.web.controller.constants;

public interface NoteControllerConstants {
	
	interface Pages {
		final String _notes = "notes";
		
		public static final String ADD 		= _notes + "/new";
		public static final String DETAILS 	= _notes + "/details";
		public static final String LISTING 	= _notes + "/listing";
	}
	
	interface ModelAttrKeys {
		interface Form {
			public static final String Add	= "noteForm";
			public static final String Date = "dateForm";
		}

		interface View {			
			public static final String Pagination = "notesPaginationData";
			public static final String Note 	  = "note";
		}
	}
	
	interface URLs {		
		public static final String MANAGER			= "/notesmanager";
		public static final String SHOW				= "/show";
		public static final String SHOW_FULL		= MANAGER + SHOW;
		public static final String ADD				= "/add";
		public static final String ADD_FULL			= MANAGER + ADD;
		public static final String ADD_POST 		= "/add.do";
		public static final String ADD_POST_FULL	= MANAGER + ADD_POST;
		public static final String DELETE			= "/delete/{noteId}";
		public static final String DELETE_FULL		= MANAGER + DELETE;
		public static final String DETAILS 			= "/details/{noteId}";
		public static final String DETAILS_FULL		= MANAGER + DETAILS;
	}
	
	interface Defaults {
		interface Pagination {
			public static final int MAX_LINKED_PAGES		= 5;
			public static final int ENTRIES_PER_PAGE 		= 15;
			public static final String DEFAULT_SORT_COLUMN 	= "author.nick";
		}
	}
}
