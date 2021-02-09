package kz.kaps.resort.core.usecase.user;

import kz.kaps.resort.core.communication.SMSService;
import kz.kaps.resort.core.constants.Constants;
import kz.kaps.resort.core.domain.PasswordResetToken;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.exception.UserNotFoundException;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public class SendPasswordRecoverySMSTokenUseCase {

    private GetUserByUsername getUserByUsername;
    private DoesUserByUsernameExists doesUserByUsernameExists;
    private CreatePasswordResetToken createPasswordResetToken;
    private SMSService SMSService;

    public SendPasswordRecoverySMSTokenUseCase(GetUserByUsername getUserByUsername,
                                               DoesUserByUsernameExists doesUserByUsernameExists,
                                               CreatePasswordResetToken createPasswordResetToken,
                                               SMSService SMSService){
        this.getUserByUsername = getUserByUsername;
        this.doesUserByUsernameExists = doesUserByUsernameExists;
        this.createPasswordResetToken = createPasswordResetToken;
        this.SMSService = SMSService;
    }

    public void sendPasswordRecoverySMSToken(String username) throws UserNotFoundException {
        failsIfUsernameIsEmpty(username);
        failsIfUserDoesNotExist(username);

        User user = getUserByUsername.getUserByUsername(username);
        String token = generateToken();

        LocalDateTime expiryTime = generateExpiryTime();

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryTime(expiryTime)
                .used(false)
                .build();

        createPasswordResetToken.createPasswordResetToken(passwordResetToken);
        SMSService.sendSMS(username, token);
    }

    private void failsIfUsernameIsEmpty(String username){
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();
    }

    private void failsIfUserDoesNotExist(String username) throws UserNotFoundException {
        boolean exists = doesUserByUsernameExists.doesUserExistByUsername(username);
        if(!exists)
            throw new UserNotFoundException();
    }

    private String generateToken(){
        String uuid = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        return uuid.substring(uuid.length() - Constants.PASSWORD_RECOVERY_SMS_TOKEN_LENGTH, uuid.length());
    }

    private LocalDateTime generateExpiryTime(){
        LocalDateTime expiryTime = LocalDateTime.now();
        expiryTime = expiryTime.plusHours(1);
        return expiryTime;
    }

}
