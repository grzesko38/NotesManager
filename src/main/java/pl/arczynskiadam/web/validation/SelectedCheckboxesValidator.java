package pl.arczynskiadam.web.validation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
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
		String[] selections = ((SelectedCheckboxesForm) target).getSelections();
		if (selections.length == 0) {
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
