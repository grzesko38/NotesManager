package pl.arczynskiadam.web.facade.note;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import pl.arczynskiadam.core.model.note.NoteDTO;
import pl.arczynskiadam.core.model.note.NoteDetailsDTO;
import pl.arczynskiadam.core.model.note.NoteVO;
import pl.arczynskiadam.web.controller.note.NotesPagesData;

public interface NoteFacade {
	public void addNote(NoteDetailsDTO note);
	public Page<NoteVO> listNotes(int pageId, int pageSize, String sortCol, boolean asc);
	public Page<NoteVO> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, Date date);
	public void deleteNote(int id);
	public void deleteNotes(int[] ids);
	public void deleteNotes(Set<Integer> ids);
	public NoteDetailsDTO findNoteById(int id);
	public Set<Integer> convertSelectionsToNotesIds(Set<String> selections);
	public Set<String> convertNotesIdsToSelections(Set<Integer> ids);
}
