package pl.arczynskiadam.web.facade.constants;

public interface NotesListingPageConstants {
	interface Defaults {
		interface Pagination {
			public static final int DEFAULT_FIRST_PAGE				= 0;
			public static final int DEFAULT_MAX_LINKED_PAGES		= 5;
			public static final int DEFAULT_ENTRIES_PER_PAGE 		= 15;
			public static final String DEFAULT_SORT_COLUMN 	= "author.nick";
		}
	}
}
