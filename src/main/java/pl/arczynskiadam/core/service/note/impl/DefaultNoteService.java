package pl.arczynskiadam.core.service.note.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public void deleteNotes(Set<Integer> ids) {
		noteDAO.deleteByIds(ids);
	}
	
	@Transactional
	public NoteVO findNoteById(int id) {
		return noteDAO.findOne(id);
	}

	public void setNoteDAO(NoteRepository noteDAO) {
		this.noteDAO = noteDAO;
	}
}
