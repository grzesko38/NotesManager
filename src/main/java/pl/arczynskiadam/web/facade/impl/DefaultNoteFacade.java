package pl.arczynskiadam.web.facade.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import pl.arczynskiadam.core.model.AnonymousUserModel;
import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;
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
	public int getNotesCountForUser(String userNick) {
		return noteService.getNotesCountForUser(userNick);
	}
	
	@Override
	@Transactional
	public void addNewNote(String noteContent, String userNick) {
		RegisteredUserModel currentUser = userService.getCurrentUser();
		
		if (currentUser != null)
		{
			addNewNote(noteContent);
		}
		else
		{
			AnonymousUserModel anonymous = userService.findAnonymousUserByNick(userNick);
			if (anonymous == null) {
				anonymous = new AnonymousUserModel();
				anonymous.setNick(userNick);
			}
			
			NoteModel note = new NoteModel();
			note.setContent(noteContent);
			note.setDateCreated(new Date());
			anonymous.addNote(note);
			
			noteService.saveNewNote(note);
		}
	}
	
	@Override
	public void addNewNote(String noteContent) {
		RegisteredUserModel user = userService.getCurrentUser();
		NoteModel note = new NoteModel();
		note.setContent(noteContent);
		note.setDateCreated(new Date());
		user.addNote(note);
		noteService.saveNewNote(note);
	}

	@Override
	public Page<NoteModel> listNotes(int pageId, int pageSize, String sortCol, boolean asc) {
		Page<NoteModel> result = noteService.listNotes(pageId, pageSize, sortCol, asc);
		fillFormattedDates(result.getContent());
		return result;
	}
	
	@Override
	public Page<NoteModel> listNotesFromDate(int pageId, int pageSize, String sortCol, boolean asc, Date date) {
		if (date == null) {
			throw new IllegalArgumentException("date cannot be null.");
		}
		
		Page<NoteModel> result = noteService.listNotesFromDate(pageId, pageSize, sortCol, asc, date);
		fillFormattedDates(result.getContent());
		return result;
	}
	
	@Override
	public NoteModel findNoteById(int id) {
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
	public void deleteNotes(Collection<Integer> ids) {
		noteService.deleteNotes(ids);
	}
	
	@Override
	public void deleteNotes(RegisteredUserModel user) {
		noteService.deleteUserNotes(user.getId());
	}
	
	@Override
	public Set<Integer> convertSelectionsToNotesIds(Collection<String> selections) {
		 return FluentIterable.from(selections).transform(new Function<String, Integer>() {
			@Override
			public Integer apply(String arg0) {
				return Integer.parseInt(arg0);
			}
		}).toSet();
	}
	
	@Override
	public Set<String> convertNotesIdsToSelections(Collection<Integer> ids) {
		return FluentIterable.from(ids).transform(new Function<Integer, String>() {
			@Override
			public String apply(Integer arg0) {
				return Integer.toString(arg0);
			}
		}).toSet();
	}

	private void fillFormattedDates(List<NoteModel> notes) {
		for (NoteModel note : notes) {		
			String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(note.getDateCreated());	
			note.setFormattedDateCreated(formattedDate);
		}
	}
}
