package pl.arczynskiadam.web.controller;

import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.ModelAttrKeys.Form.SELECTED_CHECKBOXES_FORM;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.Prefixes.REDIRECT;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.ASCENDING;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.DELETE;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.PAGE_NUMBER;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.PAGE_SIZE;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.SORT_COLUMN;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.ModelAttrKeys.Form.DATE_FILTER_FORM;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.ModelAttrKeys.View.PAGINATION;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.Pages.DETAILS;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.Pages.LISTING;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.URLs.SHOW_NOTES;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.URLs.SHOW_NOTES_FULL;
import static pl.arczynskiadam.web.facade.constants.NotesListingPageConstants.Defaults.Pagination.DEFAULT_FIRST_PAGE;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.core.service.NoteService;
import pl.arczynskiadam.web.controller.constants.GlobalControllerConstants;
import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;
import pl.arczynskiadam.web.data.NotesPaginationData;
import pl.arczynskiadam.web.facade.NoteFacade;
import pl.arczynskiadam.web.facade.UserFacade;
import pl.arczynskiadam.web.form.DateForm;
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
	
	@InitBinder(SELECTED_CHECKBOXES_FORM) //argument = command/modelattr name
		public void initBinder(WebDataBinder binder) {
		binder.addValidators(selectedCheckboxesValidator);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {SORT_COLUMN, ASCENDING})
	public String updateSortColumn(@RequestParam(value = SORT_COLUMN, required = true) String sortColumn,
			@RequestParam(value = ASCENDING, required = true) boolean ascending,
			HttpServletRequest request,	final Model model) {
		
		NotesPaginationData paginationData = noteFacade.updateSort(sortColumn, ascending);
		preparePage(paginationData, model);
		return listNotes(request, model);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {PAGE_NUMBER})
	public String updatePageNumber(@RequestParam(value = PAGE_NUMBER, required = true) int pageNumber,
			HttpServletRequest request,	final Model model) {
		
		NotesPaginationData paginationData = noteFacade.updatePageNumber(pageNumber);
		preparePage(paginationData, model);
		return listNotes(request, model);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {PAGE_SIZE})
	public String updatePageSize(@RequestParam(value = PAGE_SIZE, required = true) int pageSize,
			HttpServletRequest request,	final Model model) {
		
		NotesPaginationData paginationData = noteFacade.updatePageSize(pageSize);
		preparePage(paginationData, model);
		return listNotes(request, model);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {"!date", "!"+PAGE_NUMBER, "!"+PAGE_SIZE, "!"+SORT_COLUMN, "!"+ASCENDING})
	public String listNotes(HttpServletRequest request,	final Model model) {
		
		NotesPaginationData pagination = noteFacade.prepareNotesPaginationData();
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.PAGINATION, pagination);
		
		preparePage(pagination, model);
		populateEntriesPerPage(model);
		
		if(pagination.getPage().getNumberOfElements() == 0) {
			GlobalMessages.addWarningMessage("notes.listing.msg.noResults", model);
		}

		return NoteControllerConstants.Pages.LISTING;
	}
	
	private void preparePage(NotesPaginationData pagination, Model model)
	{
		SelectedCheckboxesForm selectedCheckboxesForm = new SelectedCheckboxesForm();
		selectedCheckboxesForm.setSelections(noteFacade.convertNotesIdsToSelections(pagination.getSelectedNotesIds()));
		model.addAttribute(SELECTED_CHECKBOXES_FORM, selectedCheckboxesForm);
		
		DateForm dateForm = new DateForm();
		dateForm.setDate(pagination.getFromDate());
		model.addAttribute(DATE_FILTER_FORM, dateForm);
		
		populateEntriesPerPage(model);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {"date"})
	public String listNotesFromDate(@ModelAttribute(SELECTED_CHECKBOXES_FORM) SelectedCheckboxesForm selectedCheckboxesForm,
			@Valid @ModelAttribute(DATE_FILTER_FORM) DateForm dateForm,
			BindingResult result,
			HttpServletRequest request,
			final Model model) {
		
		Date date = dateForm.getDate();	
		NotesPaginationData paginationData = null;
		model.addAttribute(PAGINATION, paginationData);	
		
		if (result.hasErrors()) {
			paginationData = noteFacade.prepareNotesPaginationData();
			model.addAttribute(PAGINATION, paginationData);
			selectedCheckboxesForm.setSelections(noteFacade.convertNotesIdsToSelections(paginationData.getSelectedNotesIds()));
		} else {
			if (date == null) {
				noteFacade.clearDateFilter();
				noteFacade.updatePageNumber(DEFAULT_FIRST_PAGE);
			}
			noteFacade.updateDateFilter(dateForm.getDate());
		}
				
		populateEntriesPerPage(model);
		
		return NoteControllerConstants.Pages.LISTING;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.ADD, method = RequestMethod.GET)
	public String addNote(@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.NEW_NOTE_FORM) NewNoteForm note,
			final Model model,
			HttpServletRequest request) {
		
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem("Home", NoteControllerConstants.URLs.SHOW_NOTES_FULL),
				new BreadcrumbsItem("Add note", GlobalControllerConstants.Misc.HASH));
		
		return NoteControllerConstants.Pages.ADD;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.ADD_POST, method = RequestMethod.POST)
	public String saveNote(@Validated(All.class) @ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.NEW_NOTE_FORM) NewNoteForm noteForm,
			BindingResult result,
			Model model,
			RedirectAttributes attrs) {
		
		if (result.hasErrors()) {
			createBreadcrumpAndSaveToModel(model,
					new BreadcrumbsItem("Home", NoteControllerConstants.URLs.SHOW_NOTES_FULL),
					new BreadcrumbsItem("Add note", GlobalControllerConstants.Misc.HASH));
			
			GlobalMessages.addErrorMessage("global.error.correctAll", model);
			
			return NoteControllerConstants.Pages.ADD;
		}
		
		noteFacade.addNewNote(noteForm.getContent(), noteForm.getAuthor());
		noteService.removePaginationDataFromSession();
		
		GlobalMessages.addInfoFlashMessage("notes.addNew.msg.confirmation", attrs);
		
		return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_NOTES_FULL;
	}

	@RequestMapping(value = NoteControllerConstants.URLs.DELETE, method = RequestMethod.POST)
	public String deleteNote(@PathVariable("noteId") Integer noteId) {
	
		noteFacade.deleteNote(noteId);

		return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_NOTES_FULL;
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.POST, params = {DELETE})
	public String deleteNotes(@RequestParam(value = GlobalControllerConstants.RequestParams.DELETE) String delete,
			@ModelAttribute(DATE_FILTER_FORM) DateForm dateForm,
			@Valid @ModelAttribute(SELECTED_CHECKBOXES_FORM) SelectedCheckboxesForm selectedCheckboxesForm,	
			BindingResult result,
			Model model,
			RedirectAttributes attrs) {
		
		String count = null;
		
		if ("all".equals(delete)) {
			count = Integer.toString(noteFacade.getNotesCountForUser(userFacade.getCurrentUser().getNick())); 
			noteFacade.deleteNotes(userFacade.getCurrentUser());
		} else if ("selected".equals(delete)) {
			if (result.hasErrors()) {
				NotesPaginationData pagination = noteFacade.prepareNotesPaginationData();
				model.addAttribute(PAGINATION, pagination);
				dateForm.setDate(pagination.getFromDate());
				GlobalMessages.addErrorMessage("notes.delete.msg.nothingSelected", model);
				populateEntriesPerPage(model);
				return LISTING;
			}
			count = Integer.toString(selectedCheckboxesForm.getSelections().size());
			Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
			noteFacade.deleteNotes(ids);
		}
		
		GlobalMessages.addInfoFlashMessage("notes.delete.msg.confirmation", Collections.singletonList(count), attrs);
		
		return REDIRECT + SHOW_NOTES_FULL + "?" + PAGE_NUMBER + "=" + DEFAULT_FIRST_PAGE;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.DETAILS, method = RequestMethod.GET)
	public String noteDetails(@ModelAttribute(NoteControllerConstants.ModelAttrKeys.View.NOTE) NoteModel note,
			@PathVariable("noteId") Integer noteId,
			final Model model) {
		
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem("Home", NoteControllerConstants.URLs.SHOW_NOTES_FULL),
				new BreadcrumbsItem("Note details", GlobalControllerConstants.Misc.HASH));
		
		note.setContent(noteFacade.findNoteById(noteId).getContent());

		return DETAILS;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateSelections.json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void noteSelected(@RequestBody SelectedCheckboxesForm selectedCheckboxesForm) {
		log.debug("checboxes vals to update from ajax: " + selectedCheckboxesForm.toString());
		
		NotesPaginationData sessionPagesData = noteService.retrievePagesDataFromSession();
		Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
		sessionPagesData.setSelectedNotesIds(ids);
		noteService.savePagesDataToSession(sessionPagesData);
	}
	
	private void populateEntriesPerPage(Model model) {
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.PAGE_SIZES, notesPageSizes);
	}
}