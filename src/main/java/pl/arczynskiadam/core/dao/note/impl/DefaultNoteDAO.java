package pl.arczynskiadam.core.dao.note.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.arczynskiadam.core.dao.note.NoteDAO;
import pl.arczynskiadam.core.model.note.NoteVO;

@Repository
public class DefaultNoteDAO implements NoteDAO {
	
	private static final Logger log = Logger.getLogger(DefaultNoteDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void addNote(NoteVO note) {
		log.info("note added: " + note);
		sessionFactory.getCurrentSession().save(note);
	}

	public List<NoteVO> listNotes() {
		Session session = sessionFactory.getCurrentSession();
		List<NoteVO> result = session.getNamedQuery("findAllNotes").list();
		log.info("fetched " + result.size() + " from database.");
		for (NoteVO note : result) {
			log.debug("fetched note: " + note);
		}
		return result;
	}
	
	public List<NoteVO> listNotesFromDate(Date date) {;
		Session session = sessionFactory.getCurrentSession();
		List<NoteVO> result = session.getNamedQuery("findNotesFromDate").setDate("date", date).list();
		for (NoteVO note : result) {
			log.debug("NoteDAO#listNotesFromDate: " + note);
		}
		log.info("fetched " + result.size() + " notes from database.");
		return result;
	}

	
	public void deleteNote(int id) {
		Session session = sessionFactory.getCurrentSession();
		NoteVO note = (NoteVO) session.load(NoteVO.class, id);
		if (note != null) {
			session.delete(note);
			log.info("deleted note from database: " + note.toString());
		}
	}
	
	public void deleteNotes(int[] ids) {
		List<Integer> idsList = new ArrayList<Integer>();
	    for (int i = 0; i < ids.length; i++)
	    {
	        idsList.add(ids[i]);
	    }
	    
		Session session = sessionFactory.getCurrentSession();
		session.getNamedQuery("deleteNotesByIds").setParameterList("ids", idsList).executeUpdate();
		log.info("deleted " + ids.length + " notes from database");
		log.debug("deleted ids: " + ids.toString());
	}
	
	public NoteVO findNoteById(int id) {
		return (NoteVO) sessionFactory.getCurrentSession().getNamedQuery("findNoteById").setInteger("id", id).list().get(0);
	}
}
