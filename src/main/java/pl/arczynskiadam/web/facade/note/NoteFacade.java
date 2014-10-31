package pl.arczynskiadam.web.facade.note;

import java.util.Date;
import java.util.List;
import java.util.Set;

import pl.arczynskiadam.core.model.note.NoteDTO;
import pl.arczynskiadam.core.model.note.NoteDetailsDTO;

public interface NoteFacade {
	public void addNote(NoteDetailsDTO note);
	public List<NoteDTO> listNotes();
	public List<NoteDTO> listNotesFromDate(Date date);
	public void deleteNote(int id);
	public void deleteNotes(int[] ids);
	public void deleteNotes(Set<Integer> ids);
	public NoteDetailsDTO findNoteById(int id);
	public Set<Integer> convertSelectionsToNotesIds(Set<String> selections);
	public Set<String> convertNotesIdsToSelections(Set<Integer> ids);
}
