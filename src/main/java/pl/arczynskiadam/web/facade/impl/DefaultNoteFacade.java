package pl.arczynskiadam.web.facade.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.NoteVO;
import pl.arczynskiadam.core.service.NoteService;
import pl.arczynskiadam.core.service.SessionService;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.facade.NoteFacade;

@Component
public class DefaultNoteFacade implements NoteFacade {

	@Autowired(required = true)
	private NoteService noteService;
	
	@Autowired(required = true)
	private UserService userService;
	
	@Autowired(required = true)
	private SessionService sessionService;

	@Override
	public int fetchCurrentUserNotesCount() {
		//TODO implement when login system is ready
		return 0;
	}
	
	@Override
	@Transactional
	public void addNewNoteForAnonymousUer(String noteContent, String userNick) {
		AnonymousUserVO anonymous = userService.findAnonymousUserByNick(userNick);
		if (anonymous == null) {
			anonymous = new AnonymousUserVO();
			anonymous.setNick(userNick);
		}
		
		NoteVO note = new NoteVO();
		note.setContent(noteContent);
		note.setDateCreated(new Date());
		anonymous.addNote(note);
		
		noteService.saveNewNote(note);
	}

	@Override
	public Page<NoteVO> listNotes(int pageId, int pageSize, String sortCol, boolean asc) {
		Page<NoteVO> result = noteService.listNotes(pageId, pageSize, sortCol, asc);
		fillFormattedDates(result.getContent());
		return result;
	}
	
	@Override
	public Page<NoteVO> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, Date date) {
		if (date == null) {
			throw new IllegalArgumentException("date cannot be null.");
		}
		
		Page<NoteVO> result = noteService.listNotesFromDate(pageId, pageSize, sortCol, asc, date);
		fillFormattedDates(result.getContent());
		return result;
	}
	
	@Override
	public NoteVO findNoteById(int id) {
		return noteService.findNoteById(id);
	}

	@Override
	public void deleteNote(int id) {
		noteService.deleteNote(id);
	}
	
	@Override
	public void deleteNotes(int[] ids) {
		HashSet<Integer> toDelete = new HashSet<>();
		for (int id : ids) {
			toDelete.add(new Integer(id));
		}
		deleteNotes(toDelete);
	}
	
	@Override
	public void deleteNotes(Set<Integer> ids) {
		noteService.deleteNotes(ids);
	}
	
	@Override
	public Set<Integer> convertSelectionsToNotesIds(Set<String> selections) {
		Set<Integer> ids = new HashSet<Integer>();
		
		for (String selection : selections) {
			ids.add(Integer.parseInt(selection));
		}
		
		return ids;
	}
	
	@Override
	public Set<String> convertNotesIdsToSelections(Set<Integer> ids) {
		Set<String> selections = new HashSet<String>();
		
		for (Integer id : ids) {
			selections.add(Integer.toString(id));
		}
		
		return selections;
	}

	private void fillFormattedDates(List<NoteVO> notes) {
		for (NoteVO note : notes) {		
			String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(note.getDateCreated());	
			note.setFormattedDateCreated(formattedDate);
		}
	}
}
