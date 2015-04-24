package pl.arczynskiadam.web.facade;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;

import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;

public interface NoteFacade {
	public void addNewNote(String noteContent, String userNick);
	public void addNewNote(String noteContent);
	public Page<NoteModel> listNotes(int pageId, int pageSize, String sortCol, boolean asc);
	public Page<NoteModel> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, Date date);
	public int getNotesCountForUser(String userNick);
	public void deleteNote(int id);
	public void deleteNotes(int[] ids);
	public void deleteNotes(Collection<Integer> ids);
	public void deleteNotes(RegisteredUserModel user);
	public NoteModel findNoteById(int id);
	public Set<Integer> convertSelectionsToNotesIds(Collection<String> selections);
	public Set<String> convertNotesIdsToSelections(Collection<Integer> ids);
}
