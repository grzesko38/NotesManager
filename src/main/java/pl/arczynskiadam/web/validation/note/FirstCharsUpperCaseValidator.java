package pl.arczynskiadam.web.validation.note;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class FirstCharsUpperCaseValidator implements ConstraintValidator<FirstCharsUpperCase, String> {

	int count;
	
    public void initialize(FirstCharsUpperCase constraintAnnotation) {
        this.count = constraintAnnotation.count();
    }

    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
    	
        if (object == null)
            return true;

        return object.length() >= count && StringUtils.isAllUpperCase(object.substring(0, count));
    }
}