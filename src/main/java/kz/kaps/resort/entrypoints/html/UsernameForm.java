package kz.kaps.resort.entrypoints.html;


import kz.kaps.resort.core.validators.PhoneNumberFormatConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UsernameForm {

    @NotEmpty(message = "{password-recovery.form.phone.err.required}")
    @PhoneNumberFormatConstraint(message = "{signup.form.phone.err.format}")
    private String username;

}
