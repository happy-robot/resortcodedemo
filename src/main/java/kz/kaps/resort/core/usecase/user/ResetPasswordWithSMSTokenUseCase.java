package kz.kaps.resort.core.usecase.user;

import kz.kaps.resort.core.domain.PasswordResetToken;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.exception.TokenExpiredException;
import kz.kaps.resort.core.usecase.exception.TokenNotFoundException;
import kz.kaps.resort.core.usecase.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ResetPasswordWithSMSTokenUseCase {

    private GetUserByUsername getUserByUsername;
    private UpdateUser updateUser;
    private DoesUserByUsernameExists doesUserByUsernameExists;
    private DoesNotUsedSMSTokenByUserIdExists doesNotUsedSMSTokenByUserIdExists;
    private GetPasswordResetTokensByUserId getPasswordResetTokensByUserId;
    private PasswordEncoder passwordEncoder;
    private UpdatePasswordResetToken updatePasswordResetToken;


    public ResetPasswordWithSMSTokenUseCase(GetUserByUsername getUserByUsername,
                                            UpdateUser updateUser,
                                            DoesUserByUsernameExists doesUserByUsernameExists,
                                            DoesNotUsedSMSTokenByUserIdExists doesNotUsedSMSTokenByUserIdExists,
                                            GetPasswordResetTokensByUserId getPasswordResetTokensByUserId,
                                            PasswordEncoder passwordEncoder,
                                            UpdatePasswordResetToken updatePasswordResetToken){
        this.getUserByUsername = getUserByUsername;
        this.updateUser = updateUser;
        this.doesUserByUsernameExists = doesUserByUsernameExists;
        this.doesNotUsedSMSTokenByUserIdExists = doesNotUsedSMSTokenByUserIdExists;
        this.getPasswordResetTokensByUserId = getPasswordResetTokensByUserId;
        this.passwordEncoder = passwordEncoder;
        this.updatePasswordResetToken = updatePasswordResetToken;
    }

    public void resetPasswordWithSMSTokenUseCase(String username, String newPassword, String SMSToken)
            throws UserNotFoundException, TokenNotFoundException, TokenExpiredException {

        failsIfUsernameIsEmpty(username);
        failsIfNewPasswordIsEmpty(newPassword);
        failsIfSMSTokenIsEmpty(SMSToken);
        failsIfUserDoesNotExist(username);

        User user = getUserByUsername.getUserByUsername(username);
        failsIfNotUsedSMSTokenDoesNotExist(SMSToken, user.getId());

        List<PasswordResetToken> tokens = getPasswordResetTokensByUserId.getPasswordResetTokensByUserId(user.getId());
        PasswordResetToken token = findToken(SMSToken, tokens);
        failsIfSMSTokenExpired(token);

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        updateUser.updateUser(user);

        token.setUsed(true);
        updatePasswordResetToken.updatePasswordResetToken(token);
    }

    private void failsIfUsernameIsEmpty(String username){
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();
    }

    private void failsIfNewPasswordIsEmpty(String newPassword){
        if(StringUtils.isEmpty(newPassword))
            throw new IllegalArgumentException();
    }

    private void failsIfSMSTokenIsEmpty(String SMSToken){
        if(StringUtils.isEmpty(SMSToken))
            throw new IllegalArgumentException();
    }

    private void failsIfUserDoesNotExist(String username) throws UserNotFoundException {
        boolean exists = doesUserByUsernameExists.doesUserExistByUsername(username);
        if(!exists)
            throw new UserNotFoundException();
    }

    private void failsIfNotUsedSMSTokenDoesNotExist(String SMSToken, Long userId) throws TokenNotFoundException {
        boolean exists = doesNotUsedSMSTokenByUserIdExists.doesNotUsedSMSTokenByUserIdExists(SMSToken, userId);
        if(!exists)
            throw new TokenNotFoundException();
    }

    private PasswordResetToken findToken(String token, List<PasswordResetToken> tokens) {
        Optional<PasswordResetToken> passwordResetToken =
                tokens.stream()
                        .filter(t -> !t.getUsed() && t.getToken().equals(token))
                        .sorted(
                                Comparator.comparing(PasswordResetToken::getExpiryTime).reversed()
                        )
                        .findFirst();
        return passwordResetToken.get();

    }

    private void failsIfSMSTokenExpired(PasswordResetToken passwordResetToken) throws TokenExpiredException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = passwordResetToken.getExpiryTime();

        if(expiryTime.isBefore(now))
            throw new TokenExpiredException();
    }

}
