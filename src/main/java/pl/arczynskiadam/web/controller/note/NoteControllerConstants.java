package pl.arczynskiadam.web.controller.note;

public interface NoteControllerConstants {
	
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
		public static final String showFull		= manager + show;
		public static final String add			= "/add";
		public static final String addFull		= manager + add;
		public static final String addPost 		= "/add.do";
		public static final String addPostFull	= manager + addPost;
		public static final String delete		= "/delete/{noteId}";
		public static final String deleteFull	= manager + delete;
		public static final String details 		= "/details/{noteId}";
		public static final String detailsFull	= manager + details;
	}
}