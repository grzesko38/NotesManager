package pl.arczynskiadam.core.service.note.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.arczynskiadam.core.dao.note.NoteDAO;
import pl.arczynskiadam.core.model.note.NoteVO;
import pl.arczynskiadam.core.service.note.NoteService;

@Service
public class DefaultNoteService implements NoteService {

	@Autowired
	private NoteDAO noteDAO;
	
	@Transactional
	public void addNote(NoteVO note) {
		noteDAO.addNote(note);
	}

	@Transactional
	public List<NoteVO> listNotes() {
		return noteDAO.listNotes();
	}
	
	@Transactional
	public List<NoteVO> listNotesFromDate(Date date) {
		return noteDAO.listNotesFromDate(date);
	}

	@Transactional
	public void deleteNote(int id) {
		noteDAO.deleteNote(id);
	}
	
	@Transactional
	public void deleteNotes(int[] ids) {
		noteDAO.deleteNotes(ids);
	}
	
	@Transactional
	public NoteVO findNoteById(int id) {
		return noteDAO.findNoteById(id);
	}
}
