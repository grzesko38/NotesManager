package pl.arczynskiadam.web.controller;

import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.Misc.HASH;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.ModelAttrKeys.Form.SELECTED_CHECKBOXES_FORM;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.Prefixes.REDIRECT_PREFIX;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.ASCENDING_PARAM;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.CLEAR_DATE_FILTER_PARAM;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.DATE_FILTER_FROM;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.DATE_FILTER_TO;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.DELETE_PARAM;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.PAGE_NUMBER_PARAM;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.PAGE_SIZE_PARAM;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.RequestParams.SORT_COLUMN_PARAM;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.ModelAttrKeys.Form.DATE_FILTER_FORM;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.ModelAttrKeys.Form.NOTE_FORM;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.ModelAttrKeys.View.NOTE;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.ModelAttrKeys.View.PAGINATION;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.Pages.EDIT_NOTE_PAGE;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.Pages.NOTES_LISTING_PAGE;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.Pages.NOTE_DETAILS_PAGE;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.URLs.ADD_NOTE;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.URLs.DELETE_NOTE;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.URLs.EDIT_NOTE;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.URLs.SHOW_NOTES;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.URLs.SHOW_NOTES_FULL;
import static pl.arczynskiadam.web.facade.constants.FacadesConstants.Defaults.Pagination.DEFAULT_FIRST_PAGE;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.groups.Default;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import pl.arczynskiadam.web.controller.constants.GlobalControllerConstants;
import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;
import pl.arczynskiadam.web.data.DateFilterData;
import pl.arczynskiadam.web.data.NotesPaginationData;
import pl.arczynskiadam.web.facade.NoteFacade;
import pl.arczynskiadam.web.facade.UserFacade;
import pl.arczynskiadam.web.form.DateFilterForm;
import pl.arczynskiadam.web.form.NoteForm;
import pl.arczynskiadam.web.form.SelectedCheckboxesForm;
import pl.arczynskiadam.web.form.validation.DateFilterValidator;
import pl.arczynskiadam.web.form.validation.SelectedCheckboxesValidator;
import pl.arczynskiadam.web.messages.GlobalMessages;
import pl.arczynskiadam.web.tag.navigation.BreadcrumbsItem;

@Controller
@RequestMapping(value = NoteControllerConstants.URLs.MANAGER)
public class NoteController extends AbstractController {
	
	private static final Logger log = Logger.getLogger(NoteController.class);
	
	@Autowired(required = true)
	private NoteFacade noteFacade;
	
	@Autowired(required = true)
	private UserFacade userFacade;
	
	@Resource(name="selectedNotesValidator")
    private SelectedCheckboxesValidator selectedCheckboxesValidator;
	
	@Autowired(required=true)
	DateFilterValidator dateFilterValidator;
	
	@Resource(name="notesPageSizes")
	List<Integer> notesPageSizes;
	
	@InitBinder(SELECTED_CHECKBOXES_FORM) //argument = command/modelattr name
		public void initSelectedCheckboxesValidator(WebDataBinder binder) {
		binder.addValidators(selectedCheckboxesValidator);
	}
	
	@InitBinder(DATE_FILTER_FORM) //argument = command/modelattr name
	public void initDateFilterValidator(WebDataBinder binder) {
		binder.addValidators(dateFilterValidator);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {SORT_COLUMN_PARAM, ASCENDING_PARAM})
	public String updateSortColumn(@RequestParam(value = SORT_COLUMN_PARAM, required = true) String sortColumn,
			@RequestParam(value = ASCENDING_PARAM, required = true) boolean ascending,
			HttpServletRequest request,	final Model model) {
		
		noteFacade.updateSort(sortColumn, ascending);
		return listNotes(request, model);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {PAGE_NUMBER_PARAM})
	public String updatePageNumber(@RequestParam(value = PAGE_NUMBER_PARAM, required = true) int pageNumber,
			HttpServletRequest request,	final Model model) {
		
		noteFacade.updatePageNumber(pageNumber);
		return listNotes(request, model);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {PAGE_SIZE_PARAM})
	public String updatePageSize(@RequestParam(value = PAGE_SIZE_PARAM, required = true) int pageSize,
			HttpServletRequest request,	final Model model) {
		
		noteFacade.updatePageSize(pageSize);
		return listNotes(request, model);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {
			"!"+CLEAR_DATE_FILTER_PARAM, "!"+DATE_FILTER_FROM, "!"+DATE_FILTER_TO,
			"!"+PAGE_NUMBER_PARAM, "!"+PAGE_SIZE_PARAM, "!"+SORT_COLUMN_PARAM, "!"+ASCENDING_PARAM,})
	public String listNotes(HttpServletRequest request,	final Model model) {
		
		NotesPaginationData paginationData = noteFacade.prepareNotesPaginationData();
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.PAGINATION, paginationData);
		
		preparePage(paginationData, model);
		populateEntriesPerPage(model);
		displayInfoIfNoNotes(model, paginationData);
		
		return NoteControllerConstants.Pages.NOTES_LISTING_PAGE;
	}

	private void preparePage(NotesPaginationData pagination, Model model)
	{
		SelectedCheckboxesForm selectedCheckboxesForm = new SelectedCheckboxesForm();
		selectedCheckboxesForm.setSelections(noteFacade.convertNotesIdsToSelections(pagination.getSelectedNotesIds()));
		model.addAttribute(SELECTED_CHECKBOXES_FORM, selectedCheckboxesForm);
		
		DateFilterForm dateFilterForm = new DateFilterForm();
		dateFilterForm.setFrom(pagination.getDeadlineFilter().getFrom());
		dateFilterForm.setTo(pagination.getDeadlineFilter().getTo());
		model.addAttribute(DATE_FILTER_FORM, dateFilterForm);
		
		populateEntriesPerPage(model);
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {DATE_FILTER_FROM, DATE_FILTER_TO})
	public String listNotesByDate(@ModelAttribute(SELECTED_CHECKBOXES_FORM) SelectedCheckboxesForm selectedCheckboxesForm,
			@Valid @ModelAttribute(DATE_FILTER_FORM) DateFilterForm dateFilterForm,
			BindingResult result,
			HttpServletRequest request,
			final Model model) {
		
		NotesPaginationData paginationData = null;
			
		if (result.hasErrors()) {
			paginationData = noteFacade.prepareNotesPaginationData();
			model.addAttribute(PAGINATION, paginationData);
			selectedCheckboxesForm.setSelections(noteFacade.convertNotesIdsToSelections(paginationData.getSelectedNotesIds()));
			for (ObjectError e : result.getAllErrors()) {
				if (ArrayUtils.contains(e.getCodes(), "DateFilter.dates.switched")) {
					GlobalMessages.addErrorMessage("DateFilter.dates.switched", model);
				}
				if (ArrayUtils.contains(e.getCodes(), "DateFilter.dates.empty")) {
					GlobalMessages.addErrorMessage("DateFilter.dates.empty", model);
				}
			}
		} else {
			DateFilterData dateFilter= new DateFilterData(dateFilterForm.getFrom(), dateFilterForm.getTo());
			paginationData = noteFacade.updateDateFilter(dateFilter);
		}
				
		model.addAttribute(PAGINATION, paginationData);
		populateEntriesPerPage(model);
		displayInfoIfNoNotes(model, paginationData);
		
		return NOTES_LISTING_PAGE;
	}
	
	private void displayInfoIfNoNotes(final Model model, NotesPaginationData pagination) {
		if(pagination.getPage().getNumberOfElements() == 0) {
			GlobalMessages.addWarningMessage("notes.listing.msg.noResults", model);
		}
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.GET, params = {CLEAR_DATE_FILTER_PARAM})
	public String clearDateFilter(@RequestParam(CLEAR_DATE_FILTER_PARAM) String mode) {
		
		noteFacade.clearDateFilter(mode);
		noteFacade.updatePageNumber(DEFAULT_FIRST_PAGE);
		
		return REDIRECT_PREFIX + SHOW_NOTES_FULL;
	}
	
	@RequestMapping(value = ADD_NOTE, method = RequestMethod.GET)
	public String showNewNotePage(@ModelAttribute(NOTE_FORM) NoteForm note,
			final Model model,
			HttpServletRequest request) {
		
		createAddNotePageBreadcrumbs(model);
		
		return NoteControllerConstants.Pages.NEW_NOTE_PAGE;
	}
	
	@RequestMapping(value = ADD_NOTE, method = RequestMethod.POST)
	public String saveNewNote(@Validated(Default.class) @ModelAttribute(NOTE_FORM) NoteForm noteForm,
			BindingResult bindinfgResult,
			Model model,
			RedirectAttributes attrs) {
		
		if (bindinfgResult.hasErrors()) {
			createAddNotePageBreadcrumbs(model);
			
			GlobalMessages.addErrorMessage("global.error.correctAll", model);
			
			return NoteControllerConstants.Pages.NEW_NOTE_PAGE;
		}
		
		noteFacade.addNewNote(noteForm);
		GlobalMessages.addInfoFlashMessage("notes.addNew.msg.confirmation", attrs);
		
		return REDIRECT_PREFIX + SHOW_NOTES_FULL;
	}

	@RequestMapping(value = EDIT_NOTE, method = RequestMethod.GET)
	public String showEditNotePage(@PathVariable("noteId") Integer noteId, @ModelAttribute(NOTE_FORM) NoteForm noteForm,
			Model model, RedirectAttributes attrs)
	{
		if (!noteFacade.hasCurrentUserRightsToNote(noteId)) {
			GlobalMessages.addErrorFlashMessage("global.error.permission" , attrs);
			return REDIRECT_PREFIX + SHOW_NOTES_FULL;
		}
		
		createEditNotePageBreadcrumbs(model);
		prepopulateNoteForm(noteId, noteForm);
		
		return EDIT_NOTE_PAGE;
	}
	
	private void prepopulateNoteForm(Integer noteId, NoteForm noteForm) {
		NoteModel note = noteFacade.findNoteById(noteId);
		noteForm.setId(noteId);
		noteForm.setAuthor(note.getAuthor().getNick());
		noteForm.setTitle(note.getTitle());
		noteForm.setContent(note.getContent());
		noteForm.setDeadline(LocalDate.from(note.getDeadline()));
		noteForm.setLatitude(note.getLatitude());
		noteForm.setLongitude(note.getLongitude());
	}
	
	@RequestMapping(value = EDIT_NOTE, method = RequestMethod.POST)
	public String updateNote(@ModelAttribute(NOTE_FORM) @Valid NoteForm noteForm,
			BindingResult bindinfgResult, Model model, RedirectAttributes attrs)
	{
		if (bindinfgResult.hasErrors()) {
			createEditNotePageBreadcrumbs(model);
			
			GlobalMessages.addErrorMessage("global.error.correctAll", model);
			
			return NoteControllerConstants.Pages.EDIT_NOTE_PAGE;
		}
		
		noteFacade.editNote(noteForm);
		GlobalMessages.addInfoFlashMessage("notes.edit.msg.confirmation", attrs);
		
		return REDIRECT_PREFIX + SHOW_NOTES_FULL;
	}

	@RequestMapping(value = DELETE_NOTE, method = RequestMethod.GET)
	public String deleteNote(@PathVariable("noteId") Integer noteId, RedirectAttributes attrs) {
	
		if (!noteFacade.hasCurrentUserRightsToNote(noteId)) {
			GlobalMessages.addErrorFlashMessage("global.error.permission" , attrs);
			return REDIRECT_PREFIX + SHOW_NOTES_FULL;
		}
		
		noteFacade.deleteNote(noteId);
		GlobalMessages.addInfoFlashMessage("notes.delete.single.msg.confirmation" , attrs);
		
		return REDIRECT_PREFIX + SHOW_NOTES_FULL;
	}
	
	@RequestMapping(value = SHOW_NOTES, method = RequestMethod.POST, params = {DELETE_PARAM})
	public String deleteNotes(@RequestParam(value = GlobalControllerConstants.RequestParams.DELETE_PARAM) String delete,
			@ModelAttribute(DATE_FILTER_FORM) DateFilterForm dateFilterForm,
			@Valid @ModelAttribute(SELECTED_CHECKBOXES_FORM) SelectedCheckboxesForm selectedCheckboxesForm,	
			BindingResult result,
			Model model,
			RedirectAttributes attrs) {
		
		String deletedNotesCount = null;
		
		if ("all".equals(delete)) {
			deletedNotesCount = Integer.toString(noteFacade.getNotesCountForRegisteredUser(userFacade.getCurrentUser().getNick())); 
			noteFacade.deleteNotes(userFacade.getCurrentUser());
		} else if ("selected".equals(delete)) {
			if (result.hasErrors()) {
				NotesPaginationData pagination = noteFacade.prepareNotesPaginationData();
				model.addAttribute(PAGINATION, pagination);
				dateFilterForm.setFrom(pagination.getDeadlineFilter().getFrom());
				dateFilterForm.setTo(pagination.getDeadlineFilter().getTo());
				GlobalMessages.addErrorMessage("notes.delete.msg.nothingSelected", model);
				populateEntriesPerPage(model);
				return NOTES_LISTING_PAGE;
			}
			deletedNotesCount = Integer.toString(selectedCheckboxesForm.getSelections().size());
			Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
			noteFacade.deleteNotes(ids);
		}
		
		GlobalMessages.addInfoFlashMessage("notes.delete.msg.confirmation", Collections.singletonList(deletedNotesCount), attrs);
		
		return REDIRECT_PREFIX + SHOW_NOTES_FULL;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.NOTE_DETAILS, method = RequestMethod.GET)
	public String noteDetails(@PathVariable("noteId") Integer noteId, final Model model, RedirectAttributes attrs) {
		
		if (userFacade.isCurrentUserAnonymous() && !noteFacade.isNoteCreatedByAnonymousAuthor(noteId)) {
			GlobalMessages.addErrorFlashMessage("global.edit.note.error" , attrs);
			return REDIRECT_PREFIX + SHOW_NOTES_FULL;
		}
		
		createViewNotePageBreadcrumbs(model);
		
		model.addAttribute(NOTE, noteFacade.findNoteById(noteId));
		
		return NOTE_DETAILS_PAGE;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateSelections.json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void noteSelected(@RequestBody SelectedCheckboxesForm selectedCheckboxesForm) {
		log.debug("checboxes vals to update from ajax: " + selectedCheckboxesForm.toString());
		
		NotesPaginationData sessionPagesData = noteFacade.prepareNotesPaginationData();
		Set<Integer> ids = noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections());
		sessionPagesData.setSelectedNotesIds(ids);
	}
	
	private void populateEntriesPerPage(Model model) {
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.PAGE_SIZES, notesPageSizes);
	}
	
	private void createAddNotePageBreadcrumbs(Model model) {
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem(getMessage("breadcrumbs.home"), SHOW_NOTES_FULL),
				new BreadcrumbsItem(getMessage("breadcrumbs.newNote"), HASH));
	}
	
	private void createEditNotePageBreadcrumbs(Model model) {
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem(getMessage("breadcrumbs.home"), SHOW_NOTES_FULL),
				new BreadcrumbsItem(getMessage("breadcrumbs.editNote"), GlobalControllerConstants.Misc.HASH));
	}
	
	private void createViewNotePageBreadcrumbs(Model model) {
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem(getMessage("breadcrumbs.home"), SHOW_NOTES_FULL),
				new BreadcrumbsItem(getMessage("breadcrumbs.noteDetails"), GlobalControllerConstants.Misc.HASH));
	}	
}