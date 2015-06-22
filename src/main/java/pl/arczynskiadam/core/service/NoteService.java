package pl.arczynskiadam.core.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;

import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.web.data.DateFilterData;
import pl.arczynskiadam.web.data.NotesPaginationData;

public interface NoteService {
	
	public void addNoteForRegisteredUser(NoteModel note, String userNick);
	public void addNoteForAnonymousUser(NoteModel note, String userNick);
	public Page<NoteModel> listNotes(int pageId, int pageSize, String sortCol, boolean asc);
	public Page<NoteModel> listNotesByDateFilter(int pageId, int pageSize, String sortCol, boolean asc, DateFilterData dateFilter);
	public void deleteNote(int id);
	public void deleteNotes(Collection<Integer> ids);
	public void deleteUserNotes(int userId);
	public NoteModel findNoteById(int id);
	public int getNotesCountForRegisteredUser(String userNick);
	public boolean isSessionPaginationDataAvailable();
	public NotesPaginationData retrievePagesDataFromSession();
	public void savePagesDataToSession(NotesPaginationData pagesData);
	public void clearFromDateFilter(String mode);
}
