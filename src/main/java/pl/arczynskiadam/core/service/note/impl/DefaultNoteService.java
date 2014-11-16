package pl.arczynskiadam.core.service.note.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.arczynskiadam.core.dao.note.NoteDAO;
import pl.arczynskiadam.core.dao.note.NoteRepository;
import pl.arczynskiadam.core.model.note.NoteVO;
import pl.arczynskiadam.core.service.note.NoteService;

@Service
public class DefaultNoteService implements NoteService {

	//@PersistenceContext(unitName = "pu1")
	@Resource
	private NoteRepository noteDAO;
	
	@Transactional
	public void addNote(NoteVO note) {
		noteDAO.save(note);
	}

	@Transactional
	public List<NoteVO> listNotes() {
		return noteDAO.findAll();
	}
	
	@Transactional
	public List<NoteVO> listNotesFromDate(Date date) {
		return null;//noteDAO.listNotesFromDate(date);
	}

	@Transactional
	public void deleteNote(int id) {
		noteDAO.delete(id);
	}
	
	@Transactional
	public void deleteNotes(int[] ids) {
		//noteDAO.deleteNotes(ids);
	}
	
	@Transactional
	public NoteVO findNoteById(int id) {
		return noteDAO.findOne(id);
	}

	public void setNoteDAO(NoteRepository noteDAO) {
		this.noteDAO = noteDAO;
	}
}
