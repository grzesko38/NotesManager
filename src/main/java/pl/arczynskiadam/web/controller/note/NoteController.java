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
import pl.arczynskiadam.web.tag.navigation.BreadcrumbsItem;
import pl.arczynskiadam.web.validation.SelectedCheckboxesValidator;

@Controller
@RequestMapping(value = NoteControllerConstants.URLs.MANAGER)
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
	
	@InitBinder(GlobalControllerConstants.ModelAttrKeys.Form.SelectedCheckboxes) //argument = command/modelattr name
		public void initBinder(WebDataBinder binder) {
		binder.addValidators(selectedCheckboxesValidator);
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.SHOW, method = RequestMethod.GET, params = {"!date"})
	public String listNotes(@RequestParam(value = GlobalControllerConstants.RequestParams.PAGE, required = false) Integer page,
			@RequestParam(value = GlobalControllerConstants.RequestParams.SORT_COLUMN, required = false) String sortCol,
			@RequestParam(value = GlobalControllerConstants.RequestParams.SORT_ORDER, required = false) String sortOrder,
			@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.Date) DateForm dateForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.SelectedCheckboxes) SelectedCheckboxesForm selectedCheckboxesForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.EntriesPerPage) EntriesPerPageForm entriesPerPageForm,
			HttpServletResponse response,
			final Model model) {
		
		List<NoteDTO> notes = null;
		
		NotesPagesData sessionPagesData = retrievePagesDataFromSession();
		if (sessionPagesData != null) {
			if (sessionPagesData.getFromDate() != null) {
				notes = noteFacade.listNotesFromDate(sessionPagesData.getFromDate());
			} 
		} else {
			sessionPagesData = new NotesPagesData();
		}
		if (notes == null) {
			notes = noteFacade.listNotes();
		}
		
		paginateData(sessionPagesData,
				notes,
				page,
				entriesPerPageForm.getSize() != null ? Integer.parseInt(entriesPerPageForm.getSize()) : null,
				sortCol,
				sortOrder);
		
		log.debug("sortCol: " + sessionPagesData.getPagedListHolder().getSort().getProperty());
		log.debug("SortAsc: " + sessionPagesData.getPagedListHolder().getSort().isAscending());
		
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, sessionPagesData);
		
		dateForm.setDate(sessionPagesData.getFromDate());
		selectedCheckboxesForm.setSelections(noteFacade.convertNotesIdsToSelections(sessionPagesData.getSelectedNotesIds()));
		populateEntriesPerPageForm(entriesPerPageForm, sessionPagesData.getPagedListHolder().getPageSize());

		return NoteControllerConstants.Pages.LISTING;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.SHOW, method = RequestMethod.GET, params = {"!p", "date"})
	public String listNotesFromDate(@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.EntriesPerPage) EntriesPerPageForm entriesPerPageForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.SelectedCheckboxes) SelectedCheckboxesForm selectedCheckboxesForm,
			@Valid @ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.Date) DateForm dateForm,
			BindingResult result,
			HttpServletResponse response,
			final Model model) {
		
		NotesPagesData sessionPagesData = retrievePagesDataFromSession();
		List<NoteDTO> notes = null;
		Date date = dateForm.getDate();	
		
		if (result.hasErrors()) {
			model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, sessionPagesData);
			populateEntriesPerPageForm(entriesPerPageForm);
			
			return NoteControllerConstants.Pages.LISTING;
		} else {
			sessionPagesData.setFromDate(date);
			if (date == null) {
				date = new Date(0, 0, 1);
			}
			notes = noteFacade.listNotesFromDate(date);
		}
		
		paginateData(sessionPagesData, notes);
		populateEntriesPerPageForm(entriesPerPageForm, sessionPagesData.getPagedListHolder().getPageSize());
		
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, sessionPagesData);
			
		return NoteControllerConstants.Pages.LISTING;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.ADD, method = RequestMethod.GET)
	public String addNote(@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.Add) NewNoteForm note,
			final Model model,
			HttpServletRequest request) {
		
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem("Home", NoteControllerConstants.URLs.SHOW_FULL),
				new BreadcrumbsItem("Add note", GlobalControllerConstants.Misc.HASH));
		
		return NoteControllerConstants.Pages.ADD;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.ADD_POST, method = RequestMethod.POST)
	public String saveNote(@Validated(All.class) @ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.Add) NewNoteForm note,
			BindingResult result,
			Model model) {
		
		if (result.hasErrors()) {
			createBreadcrumpAndSaveToModel(model,
					new BreadcrumbsItem("Home", NoteControllerConstants.URLs.SHOW_FULL),
					new BreadcrumbsItem("Add note", GlobalControllerConstants.Misc.HASH));
			
			return NoteControllerConstants.Pages.ADD;
		} else {
			NoteDetailsDTO noteDetails = new NoteDetailsDTO();
			noteDetails.setAuthor(note.getAuthor());
			noteDetails.setEmail(note.getEmail());
			noteDetails.setDateCreated(new Date());
			noteDetails.setContent(note.getContent());
			noteFacade.addNote(noteDetails);
			
			request.getSession().removeAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination);
			
			return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_FULL;
		}
	}

	@RequestMapping(value = NoteControllerConstants.URLs.DELETE, method = RequestMethod.POST)
	public String deleteNote(@PathVariable("noteId") Integer noteId) {
	
		noteFacade.deleteNote(noteId);

		return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_FULL;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.SHOW, method = RequestMethod.POST, params="delete")
	public String deleteNotes(@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.EntriesPerPage) EntriesPerPageForm entriesPerPageForm,
			@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.Date) DateForm dateForm,
			@Valid @ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.SelectedCheckboxes) SelectedCheckboxesForm selectedCheckboxesForm,	
			BindingResult result,
			RedirectAttributes attrs,
			final Model model) {

		if (result.hasErrors()) {
			NotesPagesData sessionPagesData = retrievePagesDataFromSession();
			if (sessionPagesData.getFromDate() != null) {
				dateForm.setDate(sessionPagesData.getFromDate());
			}
			
			model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, sessionPagesData);
			populateEntriesPerPageForm(entriesPerPageForm);
			
			return NoteControllerConstants.Pages.LISTING;
		}
		
		Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
		noteFacade.deleteNotes(ids);

		NotesPagesData sessionPagesData = retrievePagesDataFromSession();
		int page = sessionPagesData != null ? sessionPagesData.getPagedListHolder().getPage() : 0; 
		attrs.addAttribute(GlobalControllerConstants.RequestParams.PAGE, page);
		
		return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_FULL;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.DETAILS, method = RequestMethod.GET)
	public String noteDetails(@ModelAttribute(NoteControllerConstants.ModelAttrKeys.View.Note) NoteDetailsDTO note,
			@PathVariable("noteId") Integer noteId,
			final Model model) {
		
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem("Home", NoteControllerConstants.URLs.SHOW_FULL),
				new BreadcrumbsItem("Note details", GlobalControllerConstants.Misc.HASH));
		
		note.setContent(noteFacade.findNoteById(noteId).getContent());

		return NoteControllerConstants.Pages.DETAILS;
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/updateSelections.ajax", method = RequestMethod.POST, consumes = "application/json")
	public void noteSelected(@RequestBody SelectedCheckboxesForm selectedCheckboxesForm) {
		log.debug("checboxes vals to update from ajax: " + selectedCheckboxesForm.toString());
		
		NotesPagesData sessionPagesData = retrievePagesDataFromSession();
		Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
		sessionPagesData.setSelectedNotesIds(ids);
//		savePagesDataToSession(sessionPagesData);
	}
	
	private void paginateData(NotesPagesData pagesData, List<NoteDTO> notes) {
		paginateData(pagesData, notes, 0, null, null, null);
	}
	
	private void paginateData(NotesPagesData pagesData, List<NoteDTO> notes,
			Integer page, Integer size,	String sortColumn, String sortOrder) {
		
		Boolean ascending = sortOrder == null ? null : "asc".equals(sortOrder);
		
		if (retrievePagesDataFromSession() != null) {
			updatePaginationData(pagesData, notes, page, size, sortColumn, ascending);
		} else {
			paginateDataToDefaults(pagesData, notes, page, size, sortColumn, ascending);
			savePagesDataToSession(pagesData);
		}
	}
	
	private void updatePaginationData(NotesPagesData pagesData, List<NoteDTO> notes, Integer page,
			Integer size, String sortColumn, Boolean ascending) {
		
		PagedListHolder<NoteDTO> pagedListHolder = pagesData.getPagedListHolder();
		pagedListHolder.setSource(notes);
		pagesData.setPagedListHolder(pagedListHolder);
		int oldPage = pagesData.getPagedListHolder().getPage();
		
		MutableSortDefinition sort = (MutableSortDefinition) pagedListHolder.getSort();
		if (ascending != null) {
			sort.setAscending(ascending);
			clearSelectedNotesIds(pagesData);
		}
		if (sortColumn != null) {
			sort.setProperty(sortColumn);
			clearSelectedNotesIds(pagesData);
		}
		
		pagedListHolder.resort();
		
		if (size != null) {
			pagedListHolder.setPageSize(size);
			clearSelectedNotesIds(pagesData);
			page = 0;
		}
		if (page != null) {
			pagedListHolder.setPage(page);
			clearSelectedNotesIds(pagesData);
		} else {
			pagedListHolder.setPage(oldPage);
		}
		
		pagesData.setSelectedNotesIds(pagesData.getSelectedNotesIds());
		pagesData.setPagedListHolder(pagedListHolder);
	}
	
	private void paginateDataToDefaults(NotesPagesData pagesData, List<NoteDTO> notes,
			Integer page, Integer size, String sortColumn, Boolean ascending) {
		PagedListHolder<NoteDTO> pagedListHolder = new PagedListHolder<NoteDTO>(notes);

		pagedListHolder.setMaxLinkedPages(MAX_LINKED_PAGES);
		pagedListHolder.setSort(new MutableSortDefinition(
				sortColumn == null ?
						NoteControllerConstants.Defaults.DEFAULT_SORT_COLUMN : 
							sortColumn,
				true,
				ascending == null || Boolean.TRUE.equals(ascending)));
		pagedListHolder.resort();
		pagedListHolder.setPageSize(size == null ? NoteControllerConstants.Defaults.ENTRIES_PER_PAGE : size);
		pagedListHolder.setPage(page == null ? GlobalControllerConstants.Defaults.FIRST_PAGE : page);
		
		pagesData.setPagedListHolder(pagedListHolder);
	}
	
	private void clearSelectedNotesIds(NotesPagesData pagesData) {
		pagesData.getSelectedNotesIds().clear();
	}
	
	private void populateEntriesPerPageForm(EntriesPerPageForm entriesPerPageForm, int pageSize) {
		entriesPerPageForm.setPageSizes(EntriesPerPageForm.convertToPageSizesItemsList(this.notesPageSizes));
		entriesPerPageForm.setSize(Integer.toString(pageSize));
	}
	
	private void populateEntriesPerPageForm(EntriesPerPageForm entriesPerPageForm) {
		entriesPerPageForm.setPageSizes(EntriesPerPageForm.convertToPageSizesItemsList(this.notesPageSizes));
		NotesPagesData sessionPagesData = retrievePagesDataFromSession();
		if (sessionPagesData != null) {
			entriesPerPageForm.setSize(Integer.toString(sessionPagesData.getPagedListHolder().getPageSize()));
		} else {
			entriesPerPageForm.setSize(Integer.toString(NoteControllerConstants.Defaults.ENTRIES_PER_PAGE));
		}
	}
	
	private NotesPagesData retrievePagesDataFromSession() {
		NotesPagesData sessionsPagesData = (NotesPagesData) request.getSession().getAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination);
		if (sessionsPagesData == null) {
			return null;
		}
		return sessionsPagesData;
	}
	
	private void savePagesDataToSession(NotesPagesData pagesData) {
		request.getSession().setAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, pagesData);
	}
}