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
import org.springframework.data.domain.Page;
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
import pl.arczynskiadam.core.model.note.NoteVO;
import pl.arczynskiadam.core.service.SessionService;
import pl.arczynskiadam.core.service.note.NoteService;
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
	
	@Autowired
	private NoteService noteService;
	
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
			@RequestParam(value = GlobalControllerConstants.RequestParams.PAGE_SIZE, required = false) Integer size,
			@RequestParam(value = GlobalControllerConstants.RequestParams.SORT_COLUMN, required = false) String sortCol,
			@RequestParam(value = GlobalControllerConstants.RequestParams.SORT_ORDER, required = false) String sortOrder,
			@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.Date) DateForm dateForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.SelectedCheckboxes) SelectedCheckboxesForm selectedCheckboxesForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.EntriesPerPage) EntriesPerPageForm entriesPerPageForm,
			HttpServletRequest request,
			final Model model) {
		
		NotesPagesData pagination = processRequestPageParams(page, size ,sortCol, sortOrder, null);
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, pagination);
		
		populateEntriesPerPageForm(entriesPerPageForm, pagination.getPage().getSize());
		selectedCheckboxesForm.setSelections(noteFacade.convertNotesIdsToSelections(pagination.getSelectedNotesIds()));
		dateForm.setDate(pagination.getFromDate());

		return NoteControllerConstants.Pages.LISTING;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.SHOW, method = RequestMethod.GET, params = {"!p", "date"})
	public String listNotesFromDate(@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.EntriesPerPage) EntriesPerPageForm entriesPerPageForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.SelectedCheckboxes) SelectedCheckboxesForm selectedCheckboxesForm,
			@Valid @ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.Date) DateForm dateForm,
			BindingResult result,
			HttpServletResponse response,
			final Model model) {
		
		Date date = dateForm.getDate();	
		NotesPagesData pagination = null;
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, pagination);	
		
		if (result.hasErrors()) {
			pagination = processRequestPageParams(null, null, null, null, null);
			model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, pagination);
			populateEntriesPerPageForm(entriesPerPageForm);
			
			return NoteControllerConstants.Pages.LISTING;
		} else {
			Integer pageId = null;
			if (date == null) {
				noteService.retrievePagesDataFromSession().setFromDate(null);
				pageId = GlobalControllerConstants.Defaults.Pagination.FIRST_PAGE;
			}
			pagination = processRequestPageParams(pageId, null, null, null, date);
			pagination.setFromDate(date);
		}
		
		populateEntriesPerPageForm(entriesPerPageForm, pagination.getPage().getSize());
		selectedCheckboxesForm.setSelections(noteFacade.convertNotesIdsToSelections(pagination.getSelectedNotesIds()));
		
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
			
			noteService.removePaginationDataFromSession();
			
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
		
		Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
		noteFacade.deleteNotes(ids);
		
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
	
	
	@RequestMapping(value = "/resetDateFilter.ajax", method = RequestMethod.GET)
	public String resetDateFilter(@RequestBody SelectedCheckboxesForm selectedCheckboxesForm) {
		noteService.retrievePagesDataFromSession().setFromDate(null);
		
		return NoteControllerConstants.Pages.ADD;
		//return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_FULL;
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/updateSelections.ajax", method = RequestMethod.POST, consumes = "application/json")
	public void noteSelected(@RequestBody SelectedCheckboxesForm selectedCheckboxesForm) {
		log.debug("checboxes vals to update from ajax: " + selectedCheckboxesForm.toString());
		
		NotesPagesData sessionPagesData = noteService.retrievePagesDataFromSession();
		Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
		sessionPagesData.setSelectedNotesIds(ids);
		noteService.savePagesDataToSession(sessionPagesData);
	}
	
	private NotesPagesData processRequestPageParams(Integer pageId, Integer pageSize, String sortCol, String sortOrder, Date from) {
		NotesPagesData sessionPagesData = noteService.retrievePagesDataFromSession();
		
		if (sessionPagesData == null) {
			return loadDefaultPage(pageId, pageSize, sortCol, sortOrder, from);
		} else {
			return updateSessionPage(pageId, pageSize, sortCol, sortOrder, from);
		}
	}
	
	private NotesPagesData loadDefaultPage(Integer pageId, Integer pageSize, String sortCol, String sortOrder, Date from) {
		if (pageId == null) {
			pageId = GlobalControllerConstants.Defaults.Pagination.FIRST_PAGE;
		}
		if (pageSize == null) {
			pageSize = NoteControllerConstants.Defaults.Pagination.ENTRIES_PER_PAGE;
		}
		if (sortCol == null) {
			sortCol = NoteControllerConstants.Defaults.Pagination.DEFAULT_SORT_COLUMN;
		}
		
		Page<NoteVO> page = null;
		if (from == null) {
			page = noteFacade.listNotes(pageId, pageSize, sortCol, true);
		} else {
			page = noteFacade.listNotesFromDate(pageId, pageSize, sortCol, "asc".equals(sortOrder), from);
		}
		
		NotesPagesData pagination = new NotesPagesData(NoteControllerConstants.Defaults.Pagination.MAX_LINKED_PAGES);
		pagination.setPage(page);
		noteService.savePagesDataToSession(pagination);
		return pagination;
	}
	
	private NotesPagesData updateSessionPage(Integer pageId, Integer pageSize, String sortCol, String sortOrder, Date from) {
		NotesPagesData sessionPagesData = noteService.retrievePagesDataFromSession();
		
		if (pageId == null) {
			pageId = sessionPagesData.getPage().getNumber();
		} else {
			clearSelectedNotesIds(sessionPagesData);
		}
		
		if (pageSize == null) {
			pageSize = sessionPagesData.getPage().getSize();
		} else {
			pageId = GlobalControllerConstants.Defaults.Pagination.FIRST_PAGE;
			clearSelectedNotesIds(sessionPagesData);
		}
		
		if (sortCol == null) {
			sortCol = sessionPagesData.getSortCol();
		} else {
			clearSelectedNotesIds(sessionPagesData);
		}
		
		boolean asc = "asc".equals(sortOrder);
		if (sortOrder == null) {
			asc = sessionPagesData.isSortAscending();
		} else {
			clearSelectedNotesIds(sessionPagesData);
		}
		
		if (from == null) {
			from = sessionPagesData.getFromDate();
		} else {
			pageId = GlobalControllerConstants.Defaults.Pagination.FIRST_PAGE;
		}
		
		Page<NoteVO> page = null;
		if (from == null) {
			page = noteFacade.listNotes(pageId, pageSize, sortCol, asc);
		} else {
			page = noteFacade.listNotesFromDate(pageId, pageSize, sortCol, asc, from);
		}
		sessionPagesData.setPage(page);
		noteService.savePagesDataToSession(sessionPagesData);
		return sessionPagesData;
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
		NotesPagesData sessionPagesData = noteService.retrievePagesDataFromSession();
		if (sessionPagesData != null) {
			entriesPerPageForm.setSize(Integer.toString(sessionPagesData.getPage().getSize()));
		} else {
			entriesPerPageForm.setSize(Integer.toString(NoteControllerConstants.Defaults.Pagination.ENTRIES_PER_PAGE));
		}
	}
	
	
}