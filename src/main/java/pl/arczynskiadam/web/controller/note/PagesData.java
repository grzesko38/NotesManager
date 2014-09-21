package pl.arczynskiadam.web.controller.note;

import org.springframework.beans.support.PagedListHolder;

import pl.arczynskiadam.core.model.note.NoteDTO;

public class PagesData {
	PagedListHolder<NoteDTO> pagedListHolder;
	int[] selectedNotesIds;
	
	
	
	public PagedListHolder<NoteDTO> getPagedListHolder() {
		return pagedListHolder;
	}
	public void setPagedListHolder(PagedListHolder<NoteDTO> pagedListHolder) {
		this.pagedListHolder = pagedListHolder;
	}
	public int[] getSelectedNotesIds() {
		return selectedNotesIds;
	}
	public void setSelectedNotesIds(int[] selectedNotesIds) {
		this.selectedNotesIds = selectedNotesIds;
	}
}