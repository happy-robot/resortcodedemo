package kz.kaps.resort.core.validators;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberFormatValidator implements ConstraintValidator<PhoneNumberFormatConstraint, String> {

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
        if(StringUtils.isEmpty(contactField)) return true;

        boolean isValid = true;

        String phoneNumber = contactField.trim();

        if(phoneNumber.length() < 9) {
            cxt.buildConstraintViolationWithTemplate("{validator.field.size.min.9}")
                    .addConstraintViolation();
            isValid = false;
        }

        if(phoneNumber.length() >= 14) {
            cxt.buildConstraintViolationWithTemplate("{validator.field.size.max.13}")
                    .addConstraintViolation();
            isValid = false;
        }

        if(!Pattern.matches("\\+{0,1}[0-9]+", phoneNumber)){
            cxt.buildConstraintViolationWithTemplate("{validator.phonenumber.regexp}")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }

}
