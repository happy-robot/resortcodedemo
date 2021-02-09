package kz.kaps.resort.entrypoints.html;

import kz.kaps.resort.core.validators.FieldsValueMatch;
import kz.kaps.resort.core.validators.PhoneNumberFormatConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "passwordConfirm",
                message = "{password-recovery.form.password.confirm.err.not.equal}"
        )
})
public class PasswordRecoveryForm {

    @NotEmpty(message = "{password-recovery.form.phone.err.required}")
    @PhoneNumberFormatConstraint(message = "{signup.form.phone.err.format}")
    private String username;

    @NotEmpty(message = "{password-recovery.form.token.err.required}")
    private String token;

    @NotEmpty(message = "{password-recovery.form.password.err.required}")
    private String password;

    @NotEmpty(message = "{password-recovery.form.password.confirm.err.required}")
    private String passwordConfirm;

}
