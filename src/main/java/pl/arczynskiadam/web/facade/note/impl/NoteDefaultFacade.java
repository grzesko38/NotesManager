package pl.arczynskiadam.web.facade.note.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import pl.arczynskiadam.core.model.note.NoteVO;
import pl.arczynskiadam.core.service.SessionService;
import pl.arczynskiadam.core.service.note.NoteService;
import pl.arczynskiadam.web.facade.note.NoteFacade;

@Repository
public class NoteDefaultFacade implements NoteFacade {

	@Autowired(required = true)
	private NoteService noteService;
	
	@Autowired(required = true)
	private SessionService sessionService;

	@Override
	public void addNote(NoteVO note) {
		noteService.addNote(note);
	}

	@Override
	public Page<NoteVO> listNotes(int pageId, int pageSize, String sortCol, boolean asc) {
		return noteService.listNotes(pageId, pageSize, sortCol, asc);
	}
	
	@Override
	public Page<NoteVO> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, Date date) {
		if (date == null) {
			throw new IllegalArgumentException("date cannot be null.");
		}
		return noteService.listNotesFromDate(pageId, pageSize, sortCol, asc, date);
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

}
