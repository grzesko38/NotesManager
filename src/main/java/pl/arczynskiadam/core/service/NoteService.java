package pl.arczynskiadam.core.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;

import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.web.data.NotesPaginationData;

public interface NoteService {
	
	public void saveNewNote(NoteModel note);
	public Page<NoteModel> listNotes(int pageId, int pageSize, String sortCol, boolean asc);
	public Page<NoteModel> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, Date date);
	public void deleteNote(int id);
	public void deleteNotes(Collection<Integer> ids);
	public void deleteUserNotes(int userId);
	public NoteModel findNoteById(int id);
	public int getNotesCountForRegisteredUser(String userNick);
	public NotesPaginationData retrievePagesDataFromSession();
	public void savePagesDataToSession(NotesPaginationData pagesData);
	public void clearFromDateFilter();
}
