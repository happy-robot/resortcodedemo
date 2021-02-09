package kz.kaps.resort.entrypoints.html;

import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.validators.FieldsValueMatch;
import kz.kaps.resort.core.validators.PhoneNumberFormatConstraint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "passwordConfirm",
                message = "{signup.form.password.confirm.err.not.equal}"
        )
})
public class SignUpForm {

    @NotEmpty(message = "{signup.form.phone.err.required}")
    @PhoneNumberFormatConstraint(message = "{signup.form.phone.err.format}")
    private String username;

    @NotEmpty(message = "{signup.form.password.err.required}")
    private String password;

    @NotEmpty(message = "{signup.form.password.confirm.err.required}")
    private String passwordConfirm;

    public User toUser(PasswordEncoder passwordEncoder){
        return new User(username, passwordEncoder.encode(password));
    }

}
