package pl.arczynskiadam.web.validation.note;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.arczynskiadam.web.controller.note.PagesData;

@Component
public class NotesSelectionValidator implements Validator {

	private static final Logger log = Logger.getLogger(NotesSelectionValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PagesData.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.debug("NotesSelectionValidator#validate called");
		int[] selection = ((PagesData) target).getSelectedNotesIds();
		if (selection.length == 0) {
			log.debug("value rejected");
			 ((PagesData) target).setSelectedNotesIds(new int[]{66,67});
			errors.rejectValue("selectedNotesIds", "NotesSelectionForm.ids.lessThenOne");
		}
		
	}

}
