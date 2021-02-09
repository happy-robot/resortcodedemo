package kz.kaps.resort.core.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberFormatValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberFormatConstraint {

    String message() default "{signup.form.phone.err.format}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
