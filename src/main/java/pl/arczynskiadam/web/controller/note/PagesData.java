package pl.arczynskiadam.web.controller.note;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;

import pl.arczynskiadam.core.model.note.NoteDTO;

public class PagesData {
	
	private PagedListHolder<NoteDTO> pagedListHolder;
	private int[] selectedNotesIds;
	private Date fromDate;
	
	public PagesData() { }
	public PagesData(PagesData other) {
		//pagedlistholder
		this.pagedListHolder = new PagedListHolder<NoteDTO>();
		if (other.pagedListHolder != null) {
			this.pagedListHolder.setMaxLinkedPages(other.pagedListHolder.getMaxLinkedPages());
			this.pagedListHolder.setPage(other.pagedListHolder.getPage());
			this.pagedListHolder.setPageSize(other.pagedListHolder.getPageSize());
			this.pagedListHolder.setSort(new MutableSortDefinition(other.pagedListHolder.getSort()));
			this.pagedListHolder.setSource(new ArrayList<NoteDTO>(other.pagedListHolder.getSource()));
		}
		
		//selected ids
		if (other.getSelectedNotesIds() != null) {
			this.selectedNotesIds = new int[other.selectedNotesIds.length];
			for (int i = 0; i < this.selectedNotesIds.length; ++i) {
				this.selectedNotesIds[i] = other.selectedNotesIds[i];
			}
		}
		// from date
		if (other.fromDate != null) {
			this.fromDate = new Date(other.fromDate.getTime());
		} 
	}
	
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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
}