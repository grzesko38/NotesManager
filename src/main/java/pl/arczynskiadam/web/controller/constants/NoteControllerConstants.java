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
			public static final String NEW_NOTE_FORM	= "noteForm";
			public static final String DATE_FILTER_FORM = "dateForm";
		}

		interface View {			
			public static final String PAGINATION = "notesPaginationData";
			public static final String PAGE_SIZES  = "notesPageSizes";
			public static final String NOTE 	  = "note";
		}
	}
	
	interface URLs {		
		public static final String MANAGER			= "/notesmanager";
		public static final String SHOW_NOTES		= "/show";
		public static final String SHOW_NOTES_FULL	= MANAGER + SHOW_NOTES;
		public static final String ADD				= "/add";
		public static final String ADD_FULL			= MANAGER + ADD;
		public static final String ADD_POST 		= "/add";
		public static final String ADD_POST_FULL	= MANAGER + ADD_POST;
		public static final String DELETE			= "/delete/{noteId}";
		public static final String DELETE_FULL		= MANAGER + DELETE;
		public static final String DETAILS 			= "/details/{noteId}";
		public static final String DETAILS_FULL		= MANAGER + DETAILS;
	}
}
