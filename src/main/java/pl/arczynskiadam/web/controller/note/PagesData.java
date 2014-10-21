package pl.arczynskiadam.web.controller.note;

import org.springframework.beans.support.PagedListHolder;

import pl.arczynskiadam.core.model.note.NoteDTO;

public class PagesData {
	{
		sortColumn = NoteControllerConstants.Defaults.DEFAULT_SORT_COLUMN;
		sortAscending = true;
	}
	
	private PagedListHolder<NoteDTO> pagedListHolder;
	private String sortColumn;
	private boolean sortAscending;
	private int[] selectedNotesIds;
	
	public PagedListHolder<NoteDTO> getPagedListHolder() {
		return pagedListHolder;
	}
	
	public void setPagedListHolder(PagedListHolder<NoteDTO> pagedListHolder) {
		this.pagedListHolder = pagedListHolder;
	}
	
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public boolean isSortAscending() {
		return sortAscending;
	}

	public void setSortAscending(boolean sortAscending) {
		this.sortAscending = sortAscending;
	}

	public int[] getSelectedNotesIds() {
		return selectedNotesIds;
	}
	
	public void setSelectedNotesIds(int[] selectedNotesIds) {
		this.selectedNotesIds = selectedNotesIds;
	}
}