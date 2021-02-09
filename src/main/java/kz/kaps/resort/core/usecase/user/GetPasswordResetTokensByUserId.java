package kz.kaps.resort.core.usecase.user;

import kz.kaps.resort.core.domain.PasswordResetToken;

import java.util.List;

public interface GetPasswordResetTokensByUserId {

    List<PasswordResetToken> getPasswordResetTokensByUserId(Long userId);

}
