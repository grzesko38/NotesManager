package pl.arczynskiadam.web.controller.note;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
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
import pl.arczynskiadam.web.form.SelectedCheckboxesForm;
import pl.arczynskiadam.web.form.note.DateForm;
import pl.arczynskiadam.web.form.note.EntriesPerPageForm;
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
	public String listNotes(@RequestParam(value = NoteControllerConstants.RequestParams.PAGE, required = false) Integer page,
			@RequestParam(value = GlobalControllerConstants.RequestParams.ENTRIES, required = false) Integer entriesPerPage,
			@RequestParam(value = NoteControllerConstants.RequestParams.SORT_COLUMN, required = false) String sortCol,
			@RequestParam(value = NoteControllerConstants.RequestParams.SORT_ORDER, required = false) String sortOrder,
			@ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.date) DateForm dateForm,
			@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.selectedCheckboxesForm) SelectedCheckboxesForm selectedCheckboxesForm,
			HttpServletResponse response,
			final Model model) {

		populateModelWithPaginationData(model);
		PagesData pagesData = (PagesData) model.asMap().get(NoteControllerConstants.ModelAttrKeys.View.pagination);
		
		if (StringUtils.isNotBlank(sortCol)) {
			pagesData.setSortColumn(sortCol);
		}
		if(StringUtils.isNotBlank(sortOrder)) {
			 pagesData.setSortAscending("asc".equals(sortOrder));
		}
		
		pagesData.getPagedListHolder().setSort(new MutableSortDefinition(pagesData.getSortColumn(),
				true,
				pagesData.isSortAscending()));
		pagesData.getPagedListHolder().resort();
		
		if (entriesPerPage != null) {
			pagesData.getPagedListHolder().setPageSize(entriesPerPage);
		}
		if (page != null) {
			pagesData.getPagedListHolder().setPage(page);
		}
		
		log.debug("sortCol: " + pagesData.getSortColumn());
		log.debug("sortAsc: " + pagesData.isSortAscending());
			
		savePagesDataToSession(pagesData);
		
		return NoteControllerConstants.Pages.list;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.show, method = RequestMethod.GET, params = {"!p", "date"})
	public String listNotesFromDate(@ModelAttribute(GlobalControllerConstants.ModelAttrKeys.Form.selectedCheckboxesForm) SelectedCheckboxesForm selectedCheckboxesForm,
			@Valid @ModelAttribute(NoteControllerConstants.ModelAttrKeys.Form.date) DateForm dateForm,
			BindingResult result,
			HttpServletResponse response,
			final Model model) {
	    
		populateModelWithPaginationData(model);
		PagesData pagesData = (PagesData) model.asMap().get(NoteControllerConstants.ModelAttrKeys.View.pagination);
		
		List<NoteDTO> notes = null;
		Date date = dateForm.getDate();	
		if (result.hasErrors()) {
			notes = noteFacade.listNotesFromDate(new Date(0, 0, 1));
			pagesData.setFromDate(null);
		} else {
			pagesData.setFromDate(date);
			if (date == null) {
				date = new Date(0, 0, 1);
			}
			notes = noteFacade.listNotesFromDate(date);
		}
		
		pagesData.getPagedListHolder().setSource(notes);
		pagesData.getPagedListHolder().setPage(GlobalControllerConstants.Defaults.FIRST_PAGE);
		pagesData.getPagedListHolder().resort();
		populateModelWithPaginationData(model);
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
	public String deleteNotes(@Valid SelectedCheckboxesForm selectedCheckboxesForm,
			BindingResult result,
			@ModelAttribute("dateForm") DateForm dateForm,
			@RequestParam(value="date", required=false) Date date,
			@RequestParam(value="delete", required=false) String s,
			RedirectAttributes attrs,
			final Model model) {

		if (date != null) {
			dateForm.setDate(date);
		}

		if (result.hasErrors()) {
			populateModelWithSessionPaginationData(model);
			return NoteControllerConstants.Pages.list;
		}
		
		PagesData sessionPagesData = retrievePagesDataFromSession();
		int page = sessionPagesData != null ? sessionPagesData.getPagedListHolder().getPage() : 0; 
		
		noteFacade.deleteNotes(noteFacade.convertSelectionsToNotesIds(selectedCheckboxesForm.getSelections()));
		request.getSession().removeAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination);
		attrs.addAttribute(NoteControllerConstants.RequestParams.PAGE, page);

		return GlobalControllerConstants.Prefixes.redirect + NoteControllerConstants.URLs.showFull;
	}
	
	@RequestMapping(value = NoteControllerConstants.URLs.show, method = RequestMethod.POST, params="!delete")
	public String selectNotes(@ModelAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination) PagesData pagesData,
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
	
	private void populateModelWithPaginationData(Model model) {
		if (!populateModelWithSessionPaginationData(model)) {
			populateModelWithDefaultPaginationData(model);
		}
	}
	
	private void populateModelWithDefaultPaginationData(Model model) {
		PagesData pagesData = new PagesData();

		List<NoteDTO> notes = noteFacade.listNotesFromDate(new Date(0, 0, 1));
		PagedListHolder<NoteDTO> pagedListHolder = paginateData(notes,
				NoteControllerConstants.Defaults.ENTRIES_PER_PAGE,
				GlobalControllerConstants.Defaults.FIRST_PAGE);
		
		pagesData.setPagedListHolder(pagedListHolder);
		
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pagesData);
		populateModelWithPageSizes(model);
	}
	
	private boolean populateModelWithSessionPaginationData(Model model) {
		PagesData sessionPagesData = retrievePagesDataFromSession();
		
		if (sessionPagesData == null) {
			return false;
		}
		
		PagesData pagesData = new PagesData();
		pagesData.setPagedListHolder(sessionPagesData.getPagedListHolder());
		pagesData.setSelectedNotesIds(sessionPagesData.getSelectedNotesIds());
		pagesData.setSortAscending(sessionPagesData.isSortAscending());
		pagesData.setSortColumn(sessionPagesData.getSortColumn());
		pagesData.setFromDate(sessionPagesData.getFromDate());
		
		model.addAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pagesData);
		populateModelWithPageSizes(model);
		
		return true;
	}
	
	private void populateModelWithPageSizes(Model model) {
		PagesData pagesData = (PagesData) model.asMap().get(NoteControllerConstants.ModelAttrKeys.View.pagination);
		model.addAttribute(GlobalControllerConstants.ModelAttrKeys.Form.entriesPerPage,
				preparePageSizesComboBox(pagesData.getPagedListHolder().getPageSize(), this.notesPageSizes));
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
	
	private void savePagesDataToSession(PagesData pagesData) {
		request.getSession().setAttribute(NoteControllerConstants.ModelAttrKeys.View.pagination, pagesData);
	}
	
	private EntriesPerPageForm preparePageSizesComboBox(Integer selected, List<Integer> sizes) {
		log.debug("selected page size: " + selected);
		return new EntriesPerPageForm(selected, sizes);
	}
}