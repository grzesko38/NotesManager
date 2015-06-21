package pl.arczynskiadam.web.data;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Page;

import pl.arczynskiadam.core.model.NoteModel;

public class NotesPaginationData {
	{
		selectedNotesIds = new HashSet<Integer>();
		maxLinkedPages = 10;
		deadlineFilter = new DateFilterData();
	}
	
	private Page<NoteModel> page;
	private Set<Integer> selectedNotesIds;
	private DateFilterData deadlineFilter;
	private int maxLinkedPages;
	
	public NotesPaginationData() { }
	public NotesPaginationData(int maxLinkedPages) {
		this.maxLinkedPages = maxLinkedPages;
	}
	
	public String getSortCol() {
		return page.getSort().iterator().next().getProperty();
	}
	
	public boolean isSortAscending() {
		return page.getSort().getOrderFor(getSortCol()).isAscending();
	}
	
	public int getFirstLinkedPage()
	{
		if (maxLinkedPages > page.getTotalPages())
		{
			return 0;
		}
		if (page.getNumber() > page.getTotalPages() - maxLinkedPages)
		{
			return Math.min(page.getTotalPages() - maxLinkedPages, page.getNumber() - maxLinkedPages / 2);
		}
		return Math.max(0, page.getNumber() - (maxLinkedPages / 2));
	}

	public int getLastLinkedPage()
	{
		if (maxLinkedPages > page.getTotalPages())
		{
			return page.getTotalPages() - 1;
		}
		if (page.getNumber() < maxLinkedPages - 1)
		{
			return Math.max(maxLinkedPages - 1, page.getNumber() + maxLinkedPages / 2);
		}
		return Math.min(page.getNumber() + (maxLinkedPages / 2), page.getTotalPages()- 1);
	}
	
	public Page<NoteModel> getPage() {
		return page;
	}
	public void setPage(Page<NoteModel> page) {
		this.page = page;
	}
	public Set<Integer> getSelectedNotesIds() {
		return selectedNotesIds;
	}
	public void setSelectedNotesIds(Set<Integer> selectedNotesIds) {
		this.selectedNotesIds = selectedNotesIds;
	}
	
	public DateFilterData getDeadlineFilter() {
		return deadlineFilter;
	}
	public void setDeadlineFilter(DateFilterData deadlineFilter) {
		this.deadlineFilter = deadlineFilter;
	}
	public int getMaxLinkedPages() {
		return maxLinkedPages;
	}
	public void setMaxLinkedPages(int linkedPages) {
		this.maxLinkedPages = linkedPages;
	}
}