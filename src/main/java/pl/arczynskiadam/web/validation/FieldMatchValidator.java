package pl.arczynskiadam.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;


public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>
{
    private String field;
    private String confirmField;
    private FieldMatch fieldMatch;

    @Override
    public void initialize(final FieldMatch constraintAnnotation)
    {
    	fieldMatch = constraintAnnotation;
    	field = constraintAnnotation.fieldSource();
        confirmField = constraintAnnotation.fieldConfirm();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try
        {
            final Object firstObj = BeanUtils.getProperty(value, field);
            final Object secondObj = org.apache.commons.beanutils.BeanUtils.getProperty(value, confirmField);

            boolean isValid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
            if (!isValid) {
            	context.buildConstraintViolationWithTemplate(fieldMatch.message()).addNode(fieldMatch.fieldConfirm()).addConstraintViolation();
            	context.disableDefaultConstraintViolation();
            }
            
            return isValid;
        }
        catch (final Exception ignore)
        {
            // ignore
        }
        return true;
    }
}
