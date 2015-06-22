package pl.arczynskiadam.web.facade.impl;

import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.DEFAULT_ENTRIES_PER_PAGE;
import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.DEFAULT_FIRST_PAGE;
import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.DEFAULT_MAX_LINKED_PAGES;
import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.DEFAULT_SORT_COLUMN;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.service.NoteService;
import pl.arczynskiadam.core.service.SessionService;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;
import pl.arczynskiadam.web.data.DateFilterData;
import pl.arczynskiadam.web.data.NotesPaginationData;
import pl.arczynskiadam.web.facade.NoteFacade;
import pl.arczynskiadam.web.form.NewNoteForm;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

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
	@Transactional
	public void addNewNote(NewNoteForm noteData) {
		boolean isCurrentUserAnonymous = userService.getCurrentUser() == null;
		NoteModel newNote = createNewNote(noteData);
		
		if (isCurrentUserAnonymous) {
			noteService.addNoteForAnonymousUser(newNote, noteData.getAuthor());
		} else {
			noteService.addNoteForRegisteredUser(newNote, noteData.getAuthor());
		}
	}

	private NoteModel createNewNote(NewNoteForm noteData) {
		NoteModel note = new NoteModel();
		note.setTitle(noteData.getTitle());
		note.setContent(noteData.getContent());
		note.setDeadline(noteData.getDeadline());
		note.setLongutude(noteData.getLongitude());
		note.setLatitude(noteData.getLatitude());
		note.setDateCreated(new Date());
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
		NotesPaginationData paginationData = prepareNotesPaginationData();
		
		if (pageNumber == null) {
			pageNumber = paginationData.getPage().getNumber();
		} else {
			clearSelectedNotesIds(paginationData);
		}
		
		if (pageSize == null) {
			pageSize = paginationData.getPage().getSize();
		} else {
			pageNumber = DEFAULT_FIRST_PAGE;
			clearSelectedNotesIds(paginationData);
		}
		
		boolean asc;
		if (sortCol == null) {
			sortCol = paginationData.getSortCol();
			asc = paginationData.isSortAscending();
		} else {
			clearSelectedNotesIds(paginationData);
			asc = ascending;
		}
		
		if (dateFilter != null && dateFilter.isActive()) {
			pageNumber = DEFAULT_FIRST_PAGE;
		} else {
			dateFilter = paginationData.getDeadlineFilter();
		}
		
		Page<NoteModel> page = buildPage(pageNumber, pageSize, sortCol, asc, dateFilter);
		NotesPaginationData newPaginationData = buildPaginationDataFromPage(page);
		
		return newPaginationData;
	}
	
	@Override
	public NotesPaginationData prepareNotesPaginationData() {
		NotesPaginationData sessionPaginationData = noteService.retrievePagesDataFromSession();
		return sessionPaginationData != null ? updatePaginationData(sessionPaginationData) : buildDefaultPaginationData();
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
		Page<NoteModel> page = buildPage(DEFAULT_FIRST_PAGE, DEFAULT_ENTRIES_PER_PAGE, DEFAULT_SORT_COLUMN, true, new DateFilterData());
		return buildPaginationDataFromPage(page);
	}
	
	private Page<NoteModel> buildPage(Integer pageId, Integer pageSize, String sortCol, boolean asc, DateFilterData dateFilter) {
		Page<NoteModel> page = null;
		if (dateFilter.isActive()) {
			page = listNotesFromDate(pageId, pageSize, sortCol, asc, dateFilter);
		} else {
			page = listNotes(pageId, pageSize, sortCol, asc);
		}
		return page;
	}
	
	private NotesPaginationData buildPaginationDataFromPage(Page<NoteModel> page) {
		NotesPaginationData paginationData = new NotesPaginationData(DEFAULT_MAX_LINKED_PAGES);
		paginationData.setPage(page);
		noteService.savePagesDataToSession(paginationData);
		return paginationData;
	}
	
	private Page<NoteModel> listNotes(int pageId, int pageSize, String sortCol, boolean asc) {
		Page<NoteModel> result = noteService.listNotes(pageId, pageSize, sortCol, asc);
		return result;
	}
	
	private Page<NoteModel> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, DateFilterData dateFilter) {
		if (dateFilter == null) {
			throw new IllegalArgumentException("datefilter cannot be null.");
		}
		
		Page<NoteModel> result = noteService.listNotesByDateFilter(pageId, pageSize, sortCol, asc, dateFilter);
		return result;
	}
	
	private void clearSelectedNotesIds(NotesPaginationData pagesData) {
		pagesData.setSelectedNotesIds(Collections.<Integer> emptySet());
	}
	
	@Override
	public NoteModel findNoteById(int id) {
		return noteService.findNoteById(id);
	}

	@Override
	public void deleteNote(int id) {
		noteService.deleteNote(id);
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
	}
	
	@Override
	public void deleteNotes(RegisteredUserModel user) {
		noteService.deleteUserNotes(user.getId());
	}
	
	@Override
	public void removePaginationDataFromSession() {
		sessionService.getCurrentSession().removeAttribute(NoteControllerConstants.ModelAttrKeys.View.PAGINATION);
	}
	
	@Override
	public Set<Integer> convertSelectionsToNotesIds(Collection<String> selections) {
		 return FluentIterable.from(selections).transform(new Function<String, Integer>() {
			@Override
			public Integer apply(String arg0) {
				return Integer.parseInt(arg0);
			}
		}).toSet();
	}
	
	@Override
	public Set<String> convertNotesIdsToSelections(Collection<Integer> ids) {
		return FluentIterable.from(ids).transform(new Function<Integer, String>() {
			@Override
			public String apply(Integer arg0) {
				return Integer.toString(arg0);
			}
		}).toSet();
	}

	@Override
	public void clearDateFilter(String mode) {
		noteService.clearFromDateFilter(mode);
	}
}
