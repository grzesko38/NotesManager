package pl.arczynskiadam.web.form.validation;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.arczynskiadam.web.form.SelectedCheckboxesForm;

public class SelectedCheckboxesValidator implements Validator {

	private static final Logger log = Logger.getLogger(SelectedCheckboxesValidator.class);
	private String errCode = "SelectedCheckboxesForm.selections.lessThenOne";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SelectedCheckboxesForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.debug("SelectedCheckboxesValidator#validate called");
		Set<String> selections = ((SelectedCheckboxesForm) target).getSelections();
		if (selections == null || selections.size() == 0) {
			log.debug("value rejected");
			errors.rejectValue("selections", errCode);
		}	
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
}
