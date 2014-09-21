package pl.arczynskiadam.web.controller.constants.note;

import pl.arczynskiadam.web.controller.constants.GlobalControllerConstants;

public interface NoteControllerConstants extends GlobalControllerConstants {
	
	interface Pages {
		final String _notes = "notes";
		
		public static final String add 		= _notes + "/new";
		public static final String details 	= _notes + "/details";
		public static final String list 	= _notes + "/listing";
	}
	
	interface ModelAttrKeys {
		interface Form {
			public static final String add	= "noteForm";
			public static final String date = "dateForm";
		}

		interface View {			
			public static final String pagination 	= "notesPaginationData";
			public static final String note 		= "note";
		}
	}
	
	interface URLs {		
		public static final String manager		= "/notesmanager";
		public static final String show			= "/show";
		public static final String add			= "/add";
		public static final String addPost 		= "/add.do";
		public static final String deleteNote	= "/delete/{noteId}";
		public static final String details 		= "/details/{noteId}";
	}
}
