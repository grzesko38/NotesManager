package pl.arczynskiadam.web.facade;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;

import pl.arczynskiadam.core.model.NoteVO;
import pl.arczynskiadam.core.model.UserVO;

public interface NoteFacade {
	public int getCurrentUserNotesCount();
	public void addNewNote(String noteContent, String userNick);
	public void addNewNote(String noteContent);
	public Page<NoteVO> listNotes(int pageId, int pageSize, String sortCol, boolean asc);
	public Page<NoteVO> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, Date date);
	public void deleteNote(int id);
	public void deleteNotes(int[] ids);
	public void deleteNotes(Collection<Integer> ids);
	public void deleteNotes(UserVO user);
	public NoteVO findNoteById(int id);
	public Set<Integer> convertSelectionsToNotesIds(Collection<String> selections);
	public Set<String> convertNotesIdsToSelections(Collection<Integer> ids);
}
