package pl.arczynskiadam.web.facade.note.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.arczynskiadam.core.model.note.NoteDTO;
import pl.arczynskiadam.core.model.note.NoteDetailsDTO;
import pl.arczynskiadam.core.model.note.NoteVO;
import pl.arczynskiadam.core.service.note.NoteService;
import pl.arczynskiadam.web.facade.note.NoteFacade;

@Repository
public class NoteDefaultFacade implements NoteFacade {

	@Autowired
	private NoteService noteService;

	@Override
	public void addNote(NoteDetailsDTO note) {
		NoteVO noteModel = new NoteVO();
		noteModel.setAuthor(note.getAuthor());
		noteModel.setContent(note.getContent());
		noteModel.setEmail(note.getEmail());
		noteModel.setDateCreated(note.getDateCreated());
		noteService.addNote(noteModel);
	}

	@Override
	public List<NoteDTO> listNotes() {
		List<NoteVO> notes = noteService.listNotes();
		List<NoteDTO> result = new ArrayList<>();
		for (NoteVO note : notes) {
			NoteDTO n = new NoteDTO();
			n.setId(note.getId());
			n.setAuthor(note.getAuthor());
			n.setEmail(note.getEmail());
			n.setDateCreated(note.getDateCreated());
			result.add(n);
		}
		return result;
	}
	
	@Override
	public List<NoteDTO> listNotesFromDate(Date date) {
		if (date ==null) {
			throw new IllegalArgumentException("date cannot be null.");
		}
		
		List<NoteVO> notes = noteService.listNotesFromDate(date);
		List<NoteDTO> result = new ArrayList<>();
		for (NoteVO note : notes) {
			NoteDTO n = new NoteDTO();
			n.setId(note.getId());
			n.setAuthor(note.getAuthor());
			n.setEmail(note.getEmail());
			n.setDateCreated(note.getDateCreated());
			result.add(n);
		}
		return result;
	}
	
	@Override
	public NoteDetailsDTO findNoteById(int id) {
		NoteVO note = noteService.findNoteById(id);
		NoteDetailsDTO n = new NoteDetailsDTO();
		n.setId(id);
		n.setAuthor(note.getAuthor());
		n.setEmail(note.getEmail());
		n.setDateCreated(note.getDateCreated());
		n.setContent(note.getContent());
		
		return n;
	}

	@Override
	public void deleteNote(int id) {
		noteService.deleteNote(id);
	}
	
	@Override
	public void deleteNotes(int[] ids) {
		noteService.deleteNotes(ids);
	}
	
	@Override
	public void deleteNotes(Set<Integer> ids) {
		int[] convertedIds = new int[ids.size()];
		int i = 0;
		for (Integer id: ids) {
			convertedIds[i++] = id;
		}
		deleteNotes(convertedIds);
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
