package pl.arczynskiadam.core.dao.note;

import java.util.Date;
import java.util.List;

import pl.arczynskiadam.core.model.note.NoteVO;


public interface NoteDAO {
	
	public void addNote(NoteVO note);
	public List<NoteVO> listNotes();
	public List<NoteVO> listNotesFromDate(Date date);
	public NoteVO findNoteById(int id);
	public void deleteNote(int id);
	public void deleteNotes(int[] ids);
}
