package pl.arczynskiadam.web.controller.note;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;

import pl.arczynskiadam.core.model.note.NoteDTO;

public class PagesData {
	{
		selectedNotesIds = new HashSet<Integer>();
	}
	
	private PagedListHolder<NoteDTO> pagedListHolder;
	private Set<Integer> selectedNotesIds;
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
		this.selectedNotesIds = new HashSet<Integer>(other.getSelectedNotesIds());

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
	
	public Set<Integer> getSelectedNotesIds() {
		return selectedNotesIds;
	}
	public void setSelectedNotesIds(Set<Integer> selectedNotesIds) {
		this.selectedNotesIds = selectedNotesIds;
	}
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
}