package pl.arczynskiadam.web.form.validation;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pl.arczynskiadam.core.service.UserService;

public class NickAvailableValidator implements ConstraintValidator<NickAvailable, String> {

	@Resource
	private UserService userService;
	
    public void initialize(NickAvailable constraintAnnotation) {}

    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
    	
        if (object == null)
            return true;

        return userService.isNickAvailable(object);
    }
}