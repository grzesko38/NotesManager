package pl.arczynskiadam.core.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;

import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.web.data.NotesPagesData;

public interface NoteService {
	
	public void saveNewNote(NoteModel note);
	public Page<NoteModel> listNotes(int pageId, int pageSize, String sortCol, boolean asc);
	public Page<NoteModel> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, Date date);
	public void deleteNote(int id);
	public void deleteNotes(Collection<Integer> ids);
	public void deleteUserNotes(int userId);
	public NoteModel findNoteById(int id);
	public int getNotesCountForUser(String userNick);
	public NotesPagesData retrievePagesDataFromSession();
	public void savePagesDataToSession(NotesPagesData pagesData);
	public void removePaginationDataFromSession();
	public void clearFromDateFilter();
}
