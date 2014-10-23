package pl.arczynskiadam.web.controller.note;

import java.util.Date;

import org.springframework.beans.support.PagedListHolder;

import pl.arczynskiadam.core.model.note.NoteDTO;

/**
 * @author Adam
 *
 */
public class PagesData {
	{
		sortColumn = NoteControllerConstants.Defaults.DEFAULT_SORT_COLUMN;
		sortAscending = true;
	}
	
	private PagedListHolder<NoteDTO> pagedListHolder;
	private String sortColumn;
	private boolean sortAscending;
	private int[] selectedNotesIds;
	private Date fromDate;
	
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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
}