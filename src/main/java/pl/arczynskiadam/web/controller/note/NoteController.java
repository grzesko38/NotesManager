package pl.arczynskiadam.web.controller.note;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.arczynskiadam.core.model.note.NoteDTO;
import pl.arczynskiadam.core.model.note.NoteDetailsDTO;
import pl.arczynskiadam.web.controller.AbstractController;
import pl.arczynskiadam.web.controller.GlobalControllerConstants;
import pl.arczynskiadam.web.facade.note.NoteFacade;
import pl.arczynskiadam.web.form.EntriesPerPageForm;
import pl.arczynskiadam.web.form.SelectedCheckboxesForm;
import pl.arczynskiadam.web.form.note.DateForm;
import pl.arczynskiadam.web.form.note.NewNoteForm;
import pl.arczynskiadam.web.form.note.NewNoteForm.All;
import pl.arczynskiadam.web.tag.navigation.NavigationItem;
import pl.arczynskiadam.web.validation.SelectedCheckboxesValidator;

@Controller
@RequestMapping(value = NoteControllerConstants.URLs.manager)
public class NoteController extends AbstractController {
	
	private static final Logger log = Logger.getLogger(NoteController.class);
	
	private static final int MAX_LINKED_PAGES = 11;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private NoteFacade noteFacade;
	
	@Resource(name="selectedNotesValidator")
    private SelectedCheckboxesValidator selectedCheckboxesValidator;
	
	@Resource(name="notesPageSizes")
	List<Integer> notesPageSizes;
	
	@InitBinder(GlobalControllerConstants.ModelAttrKeys.Form.selectedCheckboxesForm) //argument = command/modelattr name
		public void initBinder(WebDataBinder binder) {
		binder.addValidators(selectedCheckboxesValidator);
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.show, method = RequestMethod.GET, params = {"!date"})
	public String listNotes(@RequestParam(value = GlobalControllerConstants.RequestParams.PAGE, required = false) Integer page,
			@RequestParam(value = GlobalControllerConstants.RequestParams.SORT_COLUMN, required = false) String sortCol,
			@RequestParam(value = GlobalControllerConstants.RequestParams.SORT_ORDER, required = false) String sortOrder,
			@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.date) DateForm dateForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.selectedCheckboxesForm) SelectedCheckboxesForm selectedCheckboxesForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.entriesPerPageForm) EntriesPerPageForm entriesPerPageForm,
			HttpServletResponse response,
			final Model model) {
		
		List<NoteDTO> notes = null;
		PagesData pagesData = new PagesData();
		
		PagesData sessionPagesData = retrievePagesDataFromSession();
		if (sessionPagesData != null) {
			if (sessionPagesData.getFromDate() != null) {
				notes = noteFacade.listNotesFromDate(sessionPagesData.getFromDate());
				pagesData.setFromDate(sessionPagesData.getFromDate());
				dateForm.setDate(sessionPagesData.getFromDate());
			}
			if (sessionPagesData.getSelectedNotesIds() != null) {
				pagesData.setSelectedNotesIds(sessionPagesData.getSelectedNotesIds());
				selectedCheckboxesForm.setSelections(noteFacade.convertNotesIdsToSelections(sessionPagesData.getSelectedNotesIds()));
			}
		}
		if (notes == null) {
			notes = noteFacade.listNotes();
		}
		
		pagesData.setPagedListHolder(paginateData(notes,
				page,
				entriesPerPageForm.getSize() != null ? Integer.parseInt(entriesPerPageForm.getSize()) : null,
				sortCol,
				sortOrder));
		populateEntriesPerPageForm(entriesPerPageForm, pagesData.getPagedListHolder().getPageSize());
		
		log.debug("sortCol: " + pagesData.getPagedListHolder().getSort().getProperty());
		log.debug("SortAsc: " + pagesData.getPagedListHolder().getSort().isAscending());
		
		if (request.getParameter("theme") != null || request.getParameter("lang") != null) {
			if (retrievePagesDataFromSession() != null) {
				pagesData.getPagedListHolder().setPage(retrievePagesDataFromSession().getPagedListHolder().getPage());
			}
		}
		
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pagesData);
		savePagesDataToSession(pagesData);

		return NoteControllerConstants.Pages.list;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.show, method = RequestMethod.GET, params = {"!p", "date"})
	public String listNotesFromDate(@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.entriesPerPageForm) EntriesPerPageForm entriesPerPageForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.selectedCheckboxesForm) SelectedCheckboxesForm selectedCheckboxesForm,
			@Valid @ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.date) DateForm dateForm,
			BindingResult result,
			HttpServletResponse response,
			final Model model) {
		
		PagesData pagesData = new PagesData();
		List<NoteDTO> notes = null;
		Date date = dateForm.getDate();	
		
		if (result.hasErrors()) {
			PagesData sessionPagesData = retrievePagesDataFromSession();
			model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, sessionPagesData);
			populateEntriesPerPageForm(entriesPerPageForm);
			
			return NoteControllerConstants.Pages.list;
		} else {
			pagesData.setFromDate(date);
			if (date == null) {
				date = new Date(0, 0, 1);
			}
			notes = noteFacade.listNotesFromDate(date);
		}
		
		pagesData.setPagedListHolder(paginateData(notes));
		populateEntriesPerPageForm(entriesPerPageForm, pagesData.getPagedListHolder().getPageSize());
		
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pagesData);
		savePagesDataToSession(pagesData);
			
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
			createNavigationAndSaveToModel(model,
					new NavigationItem("Home", NoteControllerConstants.URLs.showFull),
					new NavigationItem("Add note", GlobalControllerConstants.Misc.hash));
			
			return NoteControllerConstants.Pages.add;
		} else {
			NoteDetailsDTO noteDetails = new NoteDetailsDTO();
			noteDetails.setAuthor(note.getAuthor());
			noteDetails.setEmail(note.getEmail());
			noteDetails.setDateCreated(new Date());
			noteDetails.setContent(note.getContent());
			noteFacade.addNote(noteDetails);
			
			request.getSession().removeAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination);
			
			return GlobalControllerConstants.Prefixes.redirect + NoteControllerConstants.URLs.showFull;
		}
	}

	@RequestMapping(value = NoteControllerConstants.URLs.delete, method = RequestMethod.POST)
	public String deleteNote(@PathVariable("noteId") Integer noteId) {
	
		noteFacade.deleteNote(noteId);

		return GlobalControllerConstants.Prefixes.redirect + NoteControllerConstants.URLs.showFull;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.show, method = RequestMethod.POST, params="delete")
	public String deleteNotes(@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.entriesPerPageForm) EntriesPerPageForm entriesPerPageForm,
			@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.date) DateForm dateForm,
			@Valid @ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.selectedCheckboxesForm) SelectedCheckboxesForm selectedCheckboxesForm,	
			BindingResult result,
			RedirectAttributes attrs,
			final Model model) {

		if (result.hasErrors()) {
			PagesData sessionPagesData = retrievePagesDataFromSession();
			if (sessionPagesData.getFromDate() != null) {
				dateForm.setDate(sessionPagesData.getFromDate());
			}
			
			model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, sessionPagesData);
			populateEntriesPerPageForm(entriesPerPageForm);
			
			return NoteControllerConstants.Pages.list;
		}
		
		Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
		noteFacade.deleteNotes(ids);

		PagesData sessionPagesData = retrievePagesDataFromSession();
		int page = sessionPagesData != null ? sessionPagesData.getPagedListHolder().getPage() : 0; 
		attrs.addAttribute(GlobalControllerConstants.RequestParams.PAGE, page);
		
		return GlobalControllerConstants.Prefixes.redirect + NoteControllerConstants.URLs.showFull;
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
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/updateSelections.ajax", method = RequestMethod.POST, consumes = "application/json")
	public void noteSelected(@RequestBody SelectedCheckboxesForm selectedCheckboxesForm) {
		log.debug("checboxes vals to update from ajax: " + selectedCheckboxesForm.toString());
		
		PagesData sessionPagesData = retrievePagesDataFromSession();
		Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
		sessionPagesData.setSelectedNotesIds(ids);
		savePagesDataToSession(sessionPagesData);
	}
	
	private PagedListHolder<NoteDTO> paginateData(List<NoteDTO> notes) {
		return paginateData(notes, null, null, null, null);
	}
	
	private PagedListHolder<NoteDTO> paginateData(List<NoteDTO> notes, Integer page, Integer size, 
			String sortColumn, String sortOrder) {
		PagesData sessionsPagesData = retrievePagesDataFromSession();
		Boolean ascending = sortOrder == null ? null : "asc".equals(sortOrder);
		
		if (sessionsPagesData != null) {
			return updateSessionPaginationData(notes, page, size, sortColumn, ascending);
		} else {
			return paginateDataToDefaults(notes, page, size, sortColumn, ascending);
		}
	}
	
	private PagedListHolder<NoteDTO> updateSessionPaginationData(List<NoteDTO> notes, Integer page, Integer size, 
			String sortColumn, Boolean ascending) {
		
		PagesData sessionsPagesData = retrievePagesDataFromSession();
		PagedListHolder<NoteDTO> sessionPagedListHolder = null;

		sessionPagedListHolder = sessionsPagesData.getPagedListHolder();
		sessionPagedListHolder.setSource(notes);
		
		MutableSortDefinition sessionSort = (MutableSortDefinition) sessionPagedListHolder.getSort();
		if (ascending != null) {
			sessionSort.setAscending(ascending);
		}
		if (sortColumn != null) {
			sessionSort.setProperty(sortColumn);
		}
		sessionPagedListHolder.resort();
		
		if (size != null) {
			sessionPagedListHolder.setPageSize(size);
		}
		if (page != null) {
			sessionPagedListHolder.setPage(page);
		}
		
		return sessionPagedListHolder;
	}
	
	private PagedListHolder<NoteDTO> paginateDataToDefaults(List<NoteDTO> notes, Integer page, Integer size, 
			String sortColumn, Boolean ascending) {
		PagedListHolder<NoteDTO> pagedListHolder = new PagedListHolder<NoteDTO>(notes);

		pagedListHolder.setMaxLinkedPages(MAX_LINKED_PAGES);
		pagedListHolder.setSort(new MutableSortDefinition(
				sortColumn == null ?
						NoteControllerConstants.Defaults.DEFAULT_SORT_COLUMN : 
							sortColumn,
				true,
				Boolean.TRUE.equals(ascending)));
		pagedListHolder.resort();
		pagedListHolder.setPageSize(size == null ? NoteControllerConstants.Defaults.ENTRIES_PER_PAGE : size);
		pagedListHolder.setPage(page == null ? GlobalControllerConstants.Defaults.FIRST_PAGE : page);
		return pagedListHolder;
	}
	
	private PagesData retrievePagesDataFromSession() {
		PagesData sessionsPagesData = (PagesData) request.getSession().getAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination);
		if (sessionsPagesData == null) {
			return null;
		}
		return new PagesData(sessionsPagesData);
	}
	
	private void populateEntriesPerPageForm(EntriesPerPageForm entriesPerPageForm, int pageSize) {
		entriesPerPageForm.setPageSizes(EntriesPerPageForm.convertToPageSizesItemsList(this.notesPageSizes));
		entriesPerPageForm.setSize(Integer.toString(pageSize));
	}
	
	private void populateEntriesPerPageForm(EntriesPerPageForm entriesPerPageForm) {
		entriesPerPageForm.setPageSizes(EntriesPerPageForm.convertToPageSizesItemsList(this.notesPageSizes));
		PagesData sessionPagesData = retrievePagesDataFromSession();
		if (sessionPagesData != null) {
			entriesPerPageForm.setSize(Integer.toString(sessionPagesData.getPagedListHolder().getPageSize()));
		} else {
			entriesPerPageForm.setSize(Integer.toString(NoteControllerConstants.Defaults.ENTRIES_PER_PAGE));
		}
	}
	
	private void savePagesDataToSession(PagesData pagesData) {
		request.getSession().setAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pagesData);
	}
}