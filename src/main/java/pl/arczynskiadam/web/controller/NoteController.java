package pl.arczynskiadam.web.controller;

import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
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

import pl.arczynskiadam.core.model.NoteVO;
import pl.arczynskiadam.core.service.NoteService;
import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.controller.constants.GlobalControllerConstants;
import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;
import pl.arczynskiadam.web.data.NotesPagesData;
import pl.arczynskiadam.web.facade.NoteFacade;
import pl.arczynskiadam.web.facade.UserFacade;
import pl.arczynskiadam.web.form.DateForm;
import pl.arczynskiadam.web.form.EntriesPerPageForm;
import pl.arczynskiadam.web.form.NewNoteForm;
import pl.arczynskiadam.web.form.NewNoteForm.All;
import pl.arczynskiadam.web.form.SelectedCheckboxesForm;
import pl.arczynskiadam.web.form.validation.SelectedCheckboxesValidator;
import pl.arczynskiadam.web.messages.GlobalMessages;
import pl.arczynskiadam.web.tag.navigation.BreadcrumbsItem;

@Controller
@RequestMapping(value = NoteControllerConstants.URLs.MANAGER)
public class NoteController extends AbstractController {
	
	private static final Logger log = Logger.getLogger(NoteController.class);
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private NoteFacade noteFacade;
	
	@Autowired
	private UserFacade userFacade;
	
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
		
		NotesPagesData pagination = prepareNotesPage(page, size ,sortCol, sortOrder, null, model);
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, pagination);
		populateEntriesPerPageForm(entriesPerPageForm, pagination.getPage().getSize());
		selectedCheckboxesForm.setSelections(noteFacade.convertNotesIdsToSelections(pagination.getSelectedNotesIds()));
		dateForm.setDate(pagination.getFromDate());

		return NoteControllerConstants.Pages.LISTING;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.SHOW, method = RequestMethod.GET, params = {"date"})
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
			pagination = prepareNotesPage(null, null, null, null, null, model);
			model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, pagination);
			clearSelectedNotesIds(pagination);
		} else {
			Integer pageId = null;
			if (date == null) {
				noteService.clearFromDateFilter();
				pageId = GlobalControllerConstants.Defaults.Pagination.FIRST_PAGE;
			}
			pagination = prepareNotesPage(pageId, null, null, null, date, model);
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
	public String saveNote(@Validated(All.class) @ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.Add) NewNoteForm noteForm,
			BindingResult result,
			Model model,
			RedirectAttributes attrs) {
		
		if (result.hasErrors()) {
			createBreadcrumpAndSaveToModel(model,
					new BreadcrumbsItem("Home", NoteControllerConstants.URLs.SHOW_FULL),
					new BreadcrumbsItem("Add note", GlobalControllerConstants.Misc.HASH));
			
			GlobalMessages.addErrorMessage("notes.addNew.msg.error", model);
			
			return NoteControllerConstants.Pages.ADD;
		} else {		
			noteFacade.addNewNote(noteForm.getContent(), noteForm.getAuthor());
			noteService.removePaginationDataFromSession();
			
			GlobalMessages.addInfoFlashMessage("notes.addNew.msg.confirmation", attrs);
			
			return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_FULL;
		}
	}

	@RequestMapping(value = NoteControllerConstants.URLs.DELETE, method = RequestMethod.POST)
	public String deleteNote(@PathVariable("noteId") Integer noteId) {
	
		noteFacade.deleteNote(noteId);

		return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_FULL;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.SHOW, method = RequestMethod.POST, params="delete")
	public String deleteNotes(@RequestParam(value = GlobalControllerConstants.RequestParams.DELETE) String delete,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.EntriesPerPage) EntriesPerPageForm entriesPerPageForm,
			@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.Date) DateForm dateForm,
			@Valid @ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.SelectedCheckboxes) SelectedCheckboxesForm selectedCheckboxesForm,	
			BindingResult result,
			Model model,
			RedirectAttributes attrs) {
		
		String count = null;
		
		if ("all".equals(delete)) {
			count = Integer.toString(noteFacade.getNotesCountForUser(userFacade.getCurrentUser().getNick())); 
			noteFacade.deleteNotes(userFacade.getCurrentUser());
		} else if ("selected".equals(delete)) {
			if (result.hasErrors()) {
				NotesPagesData pagination = prepareNotesPage(null, null, null, null, dateForm.getDate(), model);
				model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.Pagination, pagination);
				populateEntriesPerPageForm(entriesPerPageForm);
				dateForm.setDate(pagination.getFromDate());
				GlobalMessages.addErrorMessage("notes.delete.msg.nothingSelected", model);
				return NoteControllerConstants.Pages.LISTING;
			}
			count = Integer.toString(selectedCheckboxesForm.getSelections().size());
			Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
			noteFacade.deleteNotes(ids);
		}
		
		GlobalMessages.addInfoFlashMessage("notes.delete.msg.confirmation", Collections.singletonList(count), attrs);
		
		return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_FULL;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.DETAILS, method = RequestMethod.GET)
	public String noteDetails(@ModelAttribute(NoteControllerConstants.ModelAttrKeys.View.Note) NoteVO note,
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
		
		NotesPagesData sessionPagesData = noteService.retrievePagesDataFromSession();
		Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
		sessionPagesData.setSelectedNotesIds(ids);
		noteService.savePagesDataToSession(sessionPagesData);
	}
	
	private NotesPagesData prepareNotesPage(Integer pageId, Integer pageSize, String sortCol, String sortOrder, Date from, Model model) {
		NotesPagesData sessionPagesData = noteService.retrievePagesDataFromSession();
		NotesPagesData pagination = null;
		
		if (sessionPagesData == null) {
			pagination = loadDefaultPage(pageId, pageSize, sortCol, sortOrder, from);
		} else {
			pagination = updateSessionPage(pageId, pageSize, sortCol, sortOrder, from);
		}
		
		if(pagination.getPage().getNumberOfElements() == 0) {
			GlobalMessages.addWarningMessage("notes.listing.msg.noResults", model);
		}
		
		return pagination;
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
		
		Page<NoteVO> page = buildPageSpec(pageId, pageSize, sortCol, true, from);
		
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
		
		boolean asc;
		if (sortCol == null) {
			sortCol = sessionPagesData.getSortCol();
			asc = sessionPagesData.isSortAscending();
		} else {
			clearSelectedNotesIds(sessionPagesData);
			asc = "asc".equals(sortOrder);
		}
		
		if (from == null) {
			from = sessionPagesData.getFromDate();
		} else {
			pageId = GlobalControllerConstants.Defaults.Pagination.FIRST_PAGE;
		}
		
		Page<NoteVO> page = buildPageSpec(pageId, pageSize, sortCol, asc, from);
		sessionPagesData.setPage(page);
		noteService.savePagesDataToSession(sessionPagesData);
		return sessionPagesData;
	}
	
	private Page<NoteVO> buildPageSpec(Integer pageId, Integer pageSize, String sortCol, boolean asc, Date from) {
		Page<NoteVO> page = null;
		if (from == null) {
			page = noteFacade.listNotes(pageId, pageSize, sortCol, asc);
		} else {
			page = noteFacade.listNotesFromDate(pageId, pageSize, sortCol, asc, from);
		}
		return page;
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