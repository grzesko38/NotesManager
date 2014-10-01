package pl.arczynskiadam.web.controller.note;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.arczynskiadam.core.model.note.NoteDTO;
import pl.arczynskiadam.core.model.note.NoteDetailsDTO;
import pl.arczynskiadam.web.controller.AbstractController;
import pl.arczynskiadam.web.controller.GlobalControllerConstants;
import pl.arczynskiadam.web.facade.note.NoteFacade;
import pl.arczynskiadam.web.form.note.DateForm;
import pl.arczynskiadam.web.form.note.NewNoteForm;
import pl.arczynskiadam.web.form.note.NewNoteForm.All;
import pl.arczynskiadam.web.tag.navigation.NavigationItem;
import pl.arczynskiadam.web.validation.note.NotesSelectionValidator;

@Controller
@RequestMapping(value = NoteControllerConstants.URLs.manager)
public class NoteController extends AbstractController {
	
	private static final Logger log = Logger.getLogger(NoteController.class);
	
	private static final int NOTES_PER_PAGE = 15;
	private static final int MAX_LINKED_PAGES = 11;
		
	@Autowired
	private NoteFacade noteFacade;
	
	@Autowired
    private NotesSelectionValidator notesSelectionValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	@InitBinder(NoteControllerConstants.ModelAttrKeys.View.pagination) //argument = command/modelattr name
	public void initBinder(WebDataBinder binder) {
		 binder.addValidators(notesSelectionValidator);
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.show, method = RequestMethod.GET, params={"!date"})
	public String listNotes(@RequestParam(value=NoteControllerConstants.RequestParams.PAGE, required=false) Integer page,
			@RequestParam(value=NoteControllerConstants.RequestParams.SORT_COLUMN, required=false) String sortCol,
			@RequestParam(value=NoteControllerConstants.RequestParams.SORT_ORDER, required=false) String sortOrder,
			@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.date) DateForm dateForm,
			@ModelAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination) PagesData pd,
			HttpServletResponse response,
			final Model model) {

		moveNotesPaginationDataFromSessionToModel(pd);
		
		if (StringUtils.isNotBlank(sortCol)) {
			pd.setSortColumn(sortCol);
		}
		if(StringUtils.isNotBlank(sortOrder)) {
			 pd.setSortAscending("asc".equals(sortOrder));
		}
		
		pd.getPagedListHolder().setSort(new MutableSortDefinition(pd.sortColumn, true, pd.sortAscending));
		pd.getPagedListHolder().resort();
		
		if (page != null) {
			pd.getPagedListHolder().setPage(page);
		}
		
		log.debug("sortcol: "+pd.getSortColumn());
		log.debug("sortAsc: "+pd.sortAscending);
		
		request.getSession().setAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pd);
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pd);
			
		return NoteControllerConstants.Pages.list;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.show, method = RequestMethod.GET, params={"!p", "date"})
	public String listNotesFromDate(@Valid @ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.date) DateForm dateForm,
			BindingResult result,
			HttpServletResponse response,
			final Model model) {
	    
		List<NoteDTO> notes = null;
		
		if (result.hasErrors()) {
			notes = noteFacade.listNotesFromDate(new Date(0, 0, 1));
		} else {
			Date date = dateForm.getDate();
			if (date == null) {
				date = new Date(0, 0, 1);
			}
			
			notes = noteFacade.listNotesFromDate(date);
		}
		
		PagedListHolder<NoteDTO> pagesData = paginateData(notes, NOTES_PER_PAGE, NoteControllerConstants.Defaults.FIRST_PAGE);
		PagesData pd = new PagesData();
		pd.pagedListHolder = pagesData;
		
		request.getSession().setAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pd);
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pd);
			
		return NoteControllerConstants.Pages.list;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.add, method = RequestMethod.GET)
	public String addNote(@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.add) NewNoteForm note,
			final Model model,
			HttpServletRequest request) {
		
		createNavigationAndSaveToModel(model,
				new NavigationItem("Home", NoteControllerConstants.URLs.showFull),
				new NavigationItem("Add note", GlobalControllerConstants.Misc.hash));
		
		return NoteControllerConstants.Pages.add;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.addPost, method = RequestMethod.POST)
	public String saveNote(@Validated(All.class) @ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.add) NewNoteForm note,
			BindingResult result,
			Model model) {
		
		if (result.hasErrors()) {
			return NoteControllerConstants.Pages.add;
		} else {
			NoteDetailsDTO noteDetails = new NoteDetailsDTO();
			noteDetails.setAuthor(note.getAuthor());
			noteDetails.setEmail(note.getEmail());
			noteDetails.setDateCreated(new Date());
			noteDetails.setContent(note.getContent());
			noteFacade.addNote(noteDetails);
			
			request.getSession().removeAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination);
			
			return GlobalControllerConstants.Prefixes.redirect +	NoteControllerConstants.URLs.showFull;
		}
	}

	@RequestMapping(value = NoteControllerConstants.URLs.delete, method = RequestMethod.POST)
	public String deleteNote(@PathVariable("noteId") Integer noteId) {
	
		noteFacade.deleteNote(noteId);

		return GlobalControllerConstants.Prefixes.redirect + NoteControllerConstants.URLs.showFull;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.show, method = RequestMethod.POST, params="delete")
	public String deleteNotes(@Valid @ModelAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination) PagesData pd,
			BindingResult result,
			@ModelAttribute("dateForm") DateForm dateForm,
			final Model model,
			@RequestParam(value="date", required=false) Date date,
			@RequestParam(value="delete", required=false) String s,
			RedirectAttributes attrs) {

		if (date != null) {
			dateForm.setDate(date);
		}

		if (result.hasErrors()) {
			moveNotesPaginationDataFromSessionToModel(pd);
			return NoteControllerConstants.Pages.list;
		}
		
		PagesData sessionPagesData = retrievePagesDataFromSession();
		int page = sessionPagesData != null ? sessionPagesData.getPagedListHolder().getPage() : 0; 
		
		noteFacade.deleteNotes(((PagesData)(model.asMap().get(NoteControllerConstants.ModelAttrKeys.View.pagination))).getSelectedNotesIds());
		request.getSession().removeAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination);
		attrs.addAttribute(NoteControllerConstants.RequestParams.PAGE, page);

		return GlobalControllerConstants.Prefixes.redirect + NoteControllerConstants.URLs.showFull;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.show, method = RequestMethod.POST, params="!delete")
	public String selectNotes(@ModelAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination) PagesData pd,
			BindingResult result,
			final Model model,
			@ModelAttribute("dateForm") DateForm dateForm,
			@RequestParam(value="date", required=false) Date date) {
		
		if (date != null) {
			dateForm.setDate(date);
		}
		
		return GlobalControllerConstants.Prefixes.redirect + NoteControllerConstants.Pages.list;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.details, method = RequestMethod.GET)
	public String noteDetails(@ModelAttribute(NoteControllerConstants.ModelAttrKeys.View.note) NoteDetailsDTO note,
			@PathVariable("noteId") Integer noteId,
			final Model model) {
		
		createNavigationAndSaveToModel(model,
				new NavigationItem("Home", NoteControllerConstants.URLs.showFull),
				new NavigationItem("Note details", GlobalControllerConstants.Misc.hash));
		
		note.setContent(noteFacade.findNoteById(noteId).getContent());

		return NoteControllerConstants.Pages.details;
	}
	
	private void moveNotesPaginationDataFromSessionToModel(PagesData pd) {
		PagesData sessionPagesData = retrievePagesDataFromSession();
		PagedListHolder<NoteDTO> pagedListHolder;
		int[] selectedNotesIds = null;
				
		if (sessionPagesData != null) {
			pagedListHolder = sessionPagesData.pagedListHolder;
			selectedNotesIds = sessionPagesData.selectedNotesIds;
			pd.setSortColumn(sessionPagesData.sortColumn);
			pd.setSortAscending(sessionPagesData.sortAscending);
		} else {
			List<NoteDTO> notes = noteFacade.listNotesFromDate(new Date(0, 0, 1));
			pagedListHolder = paginateData(notes, NOTES_PER_PAGE, NoteControllerConstants.Defaults.FIRST_PAGE);
		}
		
		pd.setPagedListHolder(pagedListHolder);	
		pd.setSelectedNotesIds(selectedNotesIds);
	}
	
	private PagedListHolder<NoteDTO> paginateData(List<NoteDTO> notes, int size, int page) {
		PagedListHolder<NoteDTO> pagedListHolder = new PagedListHolder<NoteDTO>(notes);
		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(size);
		pagedListHolder.setMaxLinkedPages(MAX_LINKED_PAGES);
		return pagedListHolder;
	}
	
	private PagesData retrievePagesDataFromSession() {
		return (PagesData) request.getSession().getAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination);
	}
}