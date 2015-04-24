package pl.arczynskiadam.web.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Page;

import pl.arczynskiadam.core.model.NoteModel;

public class NotesPagesData {
	{
		selectedNotesIds = new HashSet<Integer>();
		maxLinkedPages = 10;
	}
	
	private Page<NoteModel> page;
	private Set<Integer> selectedNotesIds;
	private Date fromDate;
	private int maxLinkedPages;
	
	public NotesPagesData() { }
	public NotesPagesData(int maxLinkedPages) {
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
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public int getMaxLinkedPages() {
		return maxLinkedPages;
	}
	public void setMaxLinkedPages(int linkedPages) {
		this.maxLinkedPages = linkedPages;
	}
}