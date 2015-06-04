package pl.arczynskiadam.web.facade.impl;

import static pl.arczynskiadam.web.facade.constants.NotesListingPageConstants.Defaults.Pagination.DEFAULT_ENTRIES_PER_PAGE;
import static pl.arczynskiadam.web.facade.constants.NotesListingPageConstants.Defaults.Pagination.DEFAULT_FIRST_PAGE;
import static pl.arczynskiadam.web.facade.constants.NotesListingPageConstants.Defaults.Pagination.DEFAULT_MAX_LINKED_PAGES;
import static pl.arczynskiadam.web.facade.constants.NotesListingPageConstants.Defaults.Pagination.DEFAULT_SORT_COLUMN;

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

import pl.arczynskiadam.core.model.AnonymousUserModel;
import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.service.NoteService;
import pl.arczynskiadam.core.service.SessionService;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.data.NotesPaginationData;
import pl.arczynskiadam.web.facade.NoteFacade;

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
	public int getNotesCountForUser(String userNick) {
		return noteService.getNotesCountForUser(userNick);
	}
	
	@Override
	@Transactional
	public void addNewNote(String noteContent, String userNick) {
		RegisteredUserModel currentUser = userService.getCurrentUser();
		
		if (currentUser != null)
		{
			addNewNote(noteContent);
		}
		else
		{
			AnonymousUserModel anonymous = userService.findAnonymousUserByNick(userNick);
			if (anonymous == null) {
				anonymous = new AnonymousUserModel();
				anonymous.setNick(userNick);
			}
			
			NoteModel note = new NoteModel();
			note.setContent(noteContent);
			note.setDateCreated(new Date());
			anonymous.addNote(note);
			
			noteService.saveNewNote(note);
		}
	}
	
	@Override
	public void addNewNote(String noteContent) {
		RegisteredUserModel user = userService.getCurrentUser();
		NoteModel note = new NoteModel();
		note.setContent(noteContent);
		note.setDateCreated(new Date());
		user.addNote(note);
		noteService.saveNewNote(note);
	}
	
	@Override
	public NotesPaginationData prepareNotesPaginationData() {
		NotesPaginationData sessionPaginationData = noteService.retrievePagesDataFromSession();
		return sessionPaginationData != null ? sessionPaginationData : buildDefaultPageData();
	}
	
	@Override
	public NotesPaginationData updateSort(String sortColumn, boolean ascending) {
		return updatePage(null, null, sortColumn, ascending, null);
	}
	
	@Override
	public NotesPaginationData updatePageNumber(int pageNumber) {
		return updatePage(pageNumber, null, null, null, null);
	}
	
	@Override
	public NotesPaginationData updatePageSize(int pageSize) {
		return updatePage(null, pageSize, null, null, null);		
	}
	
	@Override
	public NotesPaginationData updateDateFilter(Date from) {
		return updatePage(null, null, null, null, from);	
	}
	
	private NotesPaginationData updatePage(Integer pageNumber, Integer pageSize, String sortCol, Boolean ascending, Date from) {
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
		
		if (from == null) {
			from = paginationData.getFromDate();
		} else {
			pageNumber = DEFAULT_FIRST_PAGE;
		}
		
		Page<NoteModel> page = buildPage(pageNumber, pageSize, sortCol, asc, from);
		NotesPaginationData newPaginationData = buildPaginationDatafromPage(page);
		newPaginationData.setFromDate(from);
		
		return newPaginationData;
	}
	
	private NotesPaginationData buildDefaultPageData() {
		Page<NoteModel> page = buildPage(DEFAULT_FIRST_PAGE, DEFAULT_ENTRIES_PER_PAGE, DEFAULT_SORT_COLUMN, true, null);
		return buildPaginationDatafromPage(page);
	}
	
	private Page<NoteModel> buildPage(Integer pageId, Integer pageSize, String sortCol, boolean asc, Date from) {
		Page<NoteModel> page = null;
		if (from == null) {
			page = listNotes(pageId, pageSize, sortCol, asc);
		} else {
			page = listNotesFromDate(pageId, pageSize, sortCol, asc, from);
		}
		return page;
	}
	
	private NotesPaginationData buildPaginationDatafromPage(Page<NoteModel> page) {
		NotesPaginationData paginationData = new NotesPaginationData(DEFAULT_MAX_LINKED_PAGES);
		paginationData.setPage(page);
		noteService.savePagesDataToSession(paginationData);
		return paginationData;
	}
	
	private Page<NoteModel> listNotes(int pageId, int pageSize, String sortCol, boolean asc) {
		Page<NoteModel> result = noteService.listNotes(pageId, pageSize, sortCol, asc);
		fillFormattedDates(result.getContent());
		return result;
	}
	
	private Page<NoteModel> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, Date date) {
		if (date == null) {
			throw new IllegalArgumentException("date cannot be null.");
		}
		
		Page<NoteModel> result = noteService.listNotesFromDate(pageId, pageSize, sortCol, asc, date);
		fillFormattedDates(result.getContent());
		return result;
	}
	
	private void fillFormattedDates(List<NoteModel> notes) {
		for (NoteModel note : notes) {		
			String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(note.getDateCreated());	
			note.setFormattedDateCreated(formattedDate);
		}
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
	public void clearDateFilter() {
		noteService.clearFromDateFilter();
	}
}
