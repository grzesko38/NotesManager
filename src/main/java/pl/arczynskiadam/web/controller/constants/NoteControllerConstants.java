package pl.arczynskiadam.web.controller.constants;

public interface NoteControllerConstants {
	
	interface Pages {
		final String _notes = "notes";
		
		public static final String NEW_NOTE_PAGE		= _notes + "/new";
		public static final String EDIT_NOTE_PAGE		= _notes + "/edit";
		public static final String NOTE_DETAILS_PAGE 	= _notes + "/details";
		public static final String NOTES_LISTING_PAGE 	= _notes + "/listing";
	}
	
	interface ModelAttrKeys {
		interface Form {
			public static final String NOTE_FORM	= "noteForm";
			public static final String DATE_FILTER_FORM = "dateFilterForm";
		}

		interface View {			
			public static final String PAGINATION = "notesPaginationData";
			public static final String PAGE_SIZES = "notesPageSizes";
			public static final String NOTE 	  = "note";
		}
	}
	
	interface URLs {		
		public static final String MANAGER			 = "/notesmanager";
		public static final String SHOW_NOTES		 = "/show";
		public static final String SHOW_NOTES_FULL	 = MANAGER + SHOW_NOTES;
		public static final String ADD_NOTE			 = "/add";
		public static final String ADD_NOTE_FULL	 = MANAGER + ADD_NOTE;
		public static final String EDIT_NOTE		 = "/edit/{noteId}";
		public static final String EDIT_NOTE_FULL	 = MANAGER + EDIT_NOTE;
		public static final String UPDATE_NOTE		 = "/update";
		public static final String DELETE_NOTE		 = "/delete/{noteId}";
		public static final String DELETE_NOTE_FULL	 = MANAGER + DELETE_NOTE;
		public static final String NOTE_DETAILS 	 = "/details/{noteId}";
		public static final String NOTE_DETAILS_FULL = MANAGER + NOTE_DETAILS;
	}
}
