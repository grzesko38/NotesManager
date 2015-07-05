package pl.arczynskiadam.web.facade.impl;

import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.ANONYMOUS_USER_DEFAULT_SORT_COLUMN;
import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.DEFAULT_ENTRIES_PER_PAGE;
import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.DEFAULT_FIRST_PAGE;
import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.DEFAULT_MAX_LINKED_PAGES;
import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.REGISTERED_USER_DEFAULT_SORT_COLUMN;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.model.UserModel;
import pl.arczynskiadam.core.service.NoteService;
import pl.arczynskiadam.core.service.SessionService;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;
import pl.arczynskiadam.web.data.DateFilterData;
import pl.arczynskiadam.web.data.NotesPaginationData;
import pl.arczynskiadam.web.facade.NoteFacade;
import pl.arczynskiadam.web.form.NoteForm;

@Component
public class DefaultNoteFacade implements NoteFacade {

	@Autowired(required = true)
	private NoteService noteService;
	
	@Autowired(required = true)
	private UserService userService;
	
	@Autowired(required = true)
	private SessionService sessionService;

	@Override
	public int getNotesCountForRegisteredUser(String userNick) {
		return noteService.getNotesCountForRegisteredUser(userNick);
	}
	
	@Override
	public void addNewNote(NoteForm noteData) {
		NoteModel newNote = createNewNote(noteData);
		
		if (userService.isCurrentUserAnonymous()) {
			noteService.addNoteForAnonymousUser(newNote, noteData.getAuthor());
		} else {
			noteService.addNoteForRegisteredUser(newNote, noteData.getAuthor());
		}
	}

	private NoteModel createNewNote(NoteForm noteData) {
		NoteModel note = new NoteModel();
		note.setTitle(noteData.getTitle());
		note.setContent(noteData.getContent());
		note.setDeadline(noteData.getDeadline());
		note.setLongitude(noteData.getLongitude());
		note.setLatitude(noteData.getLatitude());
		note.setDateCreated(LocalDateTime.now());
		return note;
	}
	
	@Override
	public NotesPaginationData updateSort(String sortColumn, boolean ascending) {
		return updatePaginationData(null, null, sortColumn, ascending, null);
	}
	
	@Override
	public NotesPaginationData updatePageNumber(int pageNumber) {
		return updatePaginationData(pageNumber, null, null, null, null);
	}
	
	@Override
	public NotesPaginationData updatePageSize(int pageSize) {
		return updatePaginationData(null, pageSize, null, null, null);		
	}
	
	@Override
	public NotesPaginationData updateDateFilter(DateFilterData dateFilter) {
		return updatePaginationData(null, null, null, null, dateFilter);	
	}
	
	private NotesPaginationData updatePaginationData(Integer pageNumber, Integer pageSize, String sortCol, Boolean ascending, DateFilterData dateFilter) {
		NotesPaginationData sessionPaginationData = noteService.isSessionPaginationDataAvailable()
				? noteService.retrievePagesDataFromSession() : new NotesPaginationData(DEFAULT_MAX_LINKED_PAGES);
		Page<NoteModel> sessionPage = sessionPaginationData.getPage();
		
		if (pageNumber == null) {
			pageNumber = sessionPage != null ? sessionPage.getNumber() : DEFAULT_FIRST_PAGE;
		} else {
			clearSelectedNotesIds(sessionPaginationData);
		}
		
		if (pageSize == null) {
			pageSize = sessionPage != null ? sessionPage.getSize() : DEFAULT_ENTRIES_PER_PAGE;
		} else {
			pageNumber = DEFAULT_FIRST_PAGE;
			clearSelectedNotesIds(sessionPaginationData);
		}
		
		boolean asc;
		if (sortCol == null) {
			sortCol = sessionPage != null ? sessionPaginationData.getSortCol() : resolveDefaultSortColumn();
			asc = sessionPage != null ? sessionPaginationData.isSortAscending() : true;
		} else {
			clearSelectedNotesIds(sessionPaginationData);
			asc = ascending;
		}
		
		if (dateFilter != null && dateFilter.isActive()) {
			pageNumber = DEFAULT_FIRST_PAGE;
		} else {
			dateFilter = sessionPaginationData.getDeadlineFilter();
		}
		
		pageNumber = Math.max(0, pageNumber);
		Page<NoteModel> page = buildPage(pageNumber, pageSize, sortCol, asc, dateFilter);
		NotesPaginationData newPaginationData = buildPaginationDataFromPage(page);
		newPaginationData.setDeadlineFilter(dateFilter);
		
		return newPaginationData;
	}
	
	@Override
	public NotesPaginationData prepareNotesPaginationData() {
		return noteService.isSessionPaginationDataAvailable() ?
				updatePaginationData(noteService.retrievePagesDataFromSession()) : buildDefaultPaginationData();
	}
	
	private NotesPaginationData updatePaginationData(NotesPaginationData paginationData) {
		Page<NoteModel> sourcePage = paginationData.getPage();
		Page<NoteModel> updatedPage = buildPage(sourcePage.getNumber(),
				sourcePage.getSize(),
				paginationData.getSortCol(),
				paginationData.isSortAscending(),
				paginationData.getDeadlineFilter());
		
		paginationData.setPage(updatedPage);
		return paginationData;
	}
	
	private NotesPaginationData buildDefaultPaginationData() {
		Page<NoteModel> page = buildPage(DEFAULT_FIRST_PAGE, DEFAULT_ENTRIES_PER_PAGE, resolveDefaultSortColumn(), true, new DateFilterData());
		return buildPaginationDataFromPage(page);
	}
	
	private Page<NoteModel> buildPage(Integer pageId, Integer pageSize, String sortCol, boolean asc, DateFilterData dateFilter) {
		Page<NoteModel> page = null;
		if (dateFilter.isActive()) {
			page = noteService.listNotesByDateFilter(pageId, pageSize, sortCol, asc, dateFilter);
		} else {
			page = noteService.listNotes(pageId, pageSize, sortCol, asc);
		}
		return page;
	}
	
	private NotesPaginationData buildPaginationDataFromPage(Page<NoteModel> page) {
		NotesPaginationData paginationData = new NotesPaginationData();
		paginationData.setPage(page);
		noteService.savePagesDataToSession(paginationData);
		return paginationData;
	}
	
	private String resolveDefaultSortColumn()
	{
		return userService.isCurrentUserAnonymous() ? ANONYMOUS_USER_DEFAULT_SORT_COLUMN : REGISTERED_USER_DEFAULT_SORT_COLUMN;
	}
	
	private void clearSelectedNotesIds(NotesPaginationData pagesData) {
		pagesData.setSelectedNotesIds(Collections.<Integer> emptySet());
	}
	
	@Override
	public NoteModel findNoteById(int id) {
		return noteService.findNoteById(id);
	}

	@Override
	public boolean hasCurrentUserRightsToNote(int noteId) {
		UserModel currentUser = userService.getCurrentUser();
		if (currentUser == null) {
			return false;
		}
		
		NoteModel noteToEdit = noteService.findNoteById(noteId);
		if (noteToEdit == null)	{
			return false;
		}
		
		if (!noteToEdit.getAuthor().equals(currentUser))
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean isNoteCreatedByAnonymousAuthor(int noteId) {
		return noteService.findNoteById(noteId).getAuthor().isAnonymous();
	}
	
	@Override
	public void editNote(NoteForm noteData) {
		NoteModel note = noteService.findNoteById(noteData.getId());
		if (note == null)
		{
			throw new IllegalArgumentException("Note with given id does not exist");
		}
		
		note.setTitle(noteData.getTitle());
		note.setContent(noteData.getContent());
		note.setDeadline(noteData.getDeadline());
		note.setLatitude(noteData.getLatitude());
		note.setLongitude(noteData.getLongitude());
		noteService.updateNote(note);
	}
	
	@Override
	public void deleteNote(int id) {
		deleteNotes(Sets.newHashSet(id));
	}
	
	@Override
	public void deleteNotes(int[] ids) {
		HashSet<Integer> toDelete = new HashSet<>();
		for (int id : ids) {
			toDelete.add(new Integer(id));
		}
		deleteNotes(toDelete);
	}
	
	@Override
	public void deleteNotes(Collection<Integer> ids) {
		noteService.deleteNotes(ids);
		
		Page<NoteModel> sessionPage = noteService.retrievePagesDataFromSession().getPage();
		if (sessionPage.isLastPage() && !sessionPage.isFirstPage()) {
			if (sessionPage.getNumberOfElements() == ids.size()) {
				updatePageNumber(sessionPage.getNumber() - 1);
			}
		}
	}
	
	@Override
	public void deleteNotes(RegisteredUserModel user) {
		noteService.deleteUserNotes(user.getId());
		removePaginationDataFromSession();
	}
	
	@Override
	public void removePaginationDataFromSession() {
		sessionService.getCurrentSession().removeAttribute(NoteControllerConstants.ModelAttrKeys.View.PAGINATION);
	}
	
	@Override
	public Set<Integer> convertSelectionsToNotesIds(Collection<String> selections) {
		return selections.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toSet());
	}
	
	@Override
	public Set<String> convertNotesIdsToSelections(Collection<Integer> ids) {
		return ids.stream().map(i -> Integer.toString(i)).collect(Collectors.toSet());
	}

	@Override
	public void clearDateFilter(String mode) {
		noteService.clearFromDateFilter(mode);
	}
}
