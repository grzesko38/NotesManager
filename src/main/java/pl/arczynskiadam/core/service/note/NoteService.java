package pl.arczynskiadam.core.service.note;

import java.util.Date;
import java.util.List;

import pl.arczynskiadam.core.model.note.NoteVO;

public interface NoteService {
	
	public void addNote(NoteVO note);
	public List<NoteVO> listNotes();
	public List<NoteVO> listNotesFromDate(Date date);
	public void deleteNote(int id);
	public void deleteNotes(int[] ids);
	public NoteVO findNoteById(int id);
}
