package kz.kaps.resort.core.usecase.user;

import kz.kaps.resort.core.domain.PasswordResetToken;

public interface UpdatePasswordResetToken {

    void updatePasswordResetToken(PasswordResetToken token);

}
