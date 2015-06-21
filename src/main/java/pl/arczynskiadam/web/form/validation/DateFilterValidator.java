package pl.arczynskiadam.web.form.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.arczynskiadam.web.form.DateFilterForm;

public class DateFilterValidator implements Validator {
	
	private String bothEmptyErrCode = "DateFilter.dates.empty";
	private String switchedDatesErrCode = "DateFilter.dates.switched";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return DateFilterForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DateFilterForm form = (DateFilterForm) target;
		
		if (form.getFrom() == null && form.getTo() == null) {
			errors.rejectValue("from", bothEmptyErrCode);
		}
		if (form.getFrom() != null && form.getTo() != null) {
			if(form.getTo().compareTo(form.getFrom()) < 0) {
				errors.rejectValue("from", switchedDatesErrCode);
			}	
		}
	}

	public void setBothEmptyErrCode(String bothEmptyErrCode) {
		this.bothEmptyErrCode = bothEmptyErrCode;
	}

	public void setSwitchedDatesErrCode(String switchedDatesErrCode) {
		this.switchedDatesErrCode = switchedDatesErrCode;
	}
}

