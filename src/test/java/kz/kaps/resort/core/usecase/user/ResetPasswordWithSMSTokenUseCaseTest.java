package kz.kaps.resort.core.usecase.user;

import kz.kaps.resort.core.domain.PasswordResetToken;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.exception.TokenExpiredException;
import kz.kaps.resort.core.usecase.exception.TokenNotFoundException;
import kz.kaps.resort.core.usecase.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ResetPasswordWithSMSTokenUseCaseTest {

    @Mock
    private GetUserByUsername getUserByUsername;

    @Mock
    private DoesUserByUsernameExists doesUserByUsernameExists;

    @Mock
    private UpdateUser updateUser;

    @Mock
    private DoesNotUsedSMSTokenByUserIdExists doesNotUsedSMSTokenByUserIdExists;

    @Mock
    private GetPasswordResetTokensByUserId getPasswordResetTokensByUserId;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UpdatePasswordResetToken updatePasswordResetToken;

    @InjectMocks
    private ResetPasswordWithSMSTokenUseCase resetPasswordWithSMSTokenUseCase;


    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void errorWhenUsernameIsEmpty(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase(null, "1q2w3e4r", "1234"));
    }

    @Test
    public void errorWhenNewPasswordIsEmpty(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase("87011111111", "", "1234"));
    }

    @Test
    public void errorWhenSMSTokenIsEmpty(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase("87011111111", "1q2w3e4r", ""));
    }

    @Test
    public void errorWhenUserDoesNotExist(){
        when(doesUserByUsernameExists.doesUserExistByUsername(anyString())).thenReturn(false);

        assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(
                () -> resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase("87011111111", "1q2w3e4r", "1234"));
    }

    @Test
    public void errorWhenNotUsedSMSTokenDoesNotExist(){
        User user = getUser("87011111111", "1q2w3e4r", 1L);

        when(doesUserByUsernameExists.doesUserExistByUsername("87011111111")).thenReturn(true);
        when(getUserByUsername.getUserByUsername("87011111111")).thenReturn(user);
        when(doesNotUsedSMSTokenByUserIdExists.doesNotUsedSMSTokenByUserIdExists("1234", 1L)).thenReturn(false);


        assertThatExceptionOfType(TokenNotFoundException.class).isThrownBy(
                () -> resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase("87011111111", "1q2w3e4r", "1234"));
    }

    @Test
    public void errorWhenSMSTokenIsExpired(){
        User user = getUser("87011111111", "1q2w3e4r", 1L);

        when(doesUserByUsernameExists.doesUserExistByUsername("87011111111")).thenReturn(true);
        when(getUserByUsername.getUserByUsername("87011111111")).thenReturn(user);
        when(doesNotUsedSMSTokenByUserIdExists.doesNotUsedSMSTokenByUserIdExists("1234", 1L)).thenReturn(true);

        List<PasswordResetToken> tokens = new ArrayList<>();
        PasswordResetToken token = getExpiredToken(user, "1234", 1L, false);
        tokens.add(token);

        when(getPasswordResetTokensByUserId.getPasswordResetTokensByUserId(1L)).thenReturn(tokens);

        assertThatExceptionOfType(TokenExpiredException.class).isThrownBy(
                () -> resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase("87011111111", "1q2w3e4r", "1234"));
    }

    private User getUser(String username, String password, Long id){
        User user = new User(username, password);
        user.setId(id);
        return user;
    }

    private PasswordResetToken getExpiredToken(User user, String tokenStr, Long id, boolean used){
        LocalDateTime expiryTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        PasswordResetToken token = PasswordResetToken.builder()
                .user(user)
                .token(tokenStr)
                .id(id)
                .expiryTime(expiryTime)
                .used(used)
                .build();

        return token;
    }

    private PasswordResetToken getNotExpiredToken(User user, String tokenStr, Long id, boolean used){
        LocalDateTime expiryTime = LocalDateTime.now();
        expiryTime = expiryTime.plusHours(1);
        PasswordResetToken token = PasswordResetToken.builder()
                .user(user)
                .token(tokenStr)
                .id(id)
                .expiryTime(expiryTime)
                .used(used)
                .build();

        return token;
    }

    @Test
    public void passwordIsEncoded_Success() throws UserNotFoundException, TokenNotFoundException, TokenExpiredException {
        User user = getUser("87011111111", "1q2w3e4r", 1L);

        when(doesUserByUsernameExists.doesUserExistByUsername("87011111111")).thenReturn(true);
        when(getUserByUsername.getUserByUsername("87011111111")).thenReturn(user);
        when(doesNotUsedSMSTokenByUserIdExists.doesNotUsedSMSTokenByUserIdExists("1234", 1L)).thenReturn(true);

        List<PasswordResetToken> tokens = new ArrayList<>();
        PasswordResetToken token = getNotExpiredToken(user, "1234", 1L, false);
        tokens.add(token);

        when(getPasswordResetTokensByUserId.getPasswordResetTokensByUserId(1L)).thenReturn(tokens);
        when(passwordEncoder.encode("1q2w3e4r")).thenReturn("qwerty");

        resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase("87011111111", "1q2w3e4r", "1234");

        assertEquals("qwerty", user.getPassword());

    }

    @Test
    public void userIsSaved_Success() throws UserNotFoundException, TokenNotFoundException, TokenExpiredException {
        User user = getUser("87011111111", "1q2w3e4r", 1L);

        when(doesUserByUsernameExists.doesUserExistByUsername("87011111111")).thenReturn(true);
        when(getUserByUsername.getUserByUsername("87011111111")).thenReturn(user);
        when(doesNotUsedSMSTokenByUserIdExists.doesNotUsedSMSTokenByUserIdExists("1234", 1L)).thenReturn(true);

        List<PasswordResetToken> tokens = new ArrayList<>();
        PasswordResetToken token = getNotExpiredToken(user, "1234", 1L, false);
        tokens.add(token);

        when(getPasswordResetTokensByUserId.getPasswordResetTokensByUserId(1L)).thenReturn(tokens);
        when(passwordEncoder.encode("1q2w3e4r")).thenReturn("qwerty");

        resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase("87011111111", "1q2w3e4r", "1234");

        verify(updateUser, times(1)).updateUser(user);
    }

    @Test
    public void tokenIsSetToUsed_Success() throws UserNotFoundException, TokenNotFoundException, TokenExpiredException {
        User user = getUser("87011111111", "1q2w3e4r", 1L);

        when(doesUserByUsernameExists.doesUserExistByUsername("87011111111")).thenReturn(true);
        when(getUserByUsername.getUserByUsername("87011111111")).thenReturn(user);
        when(doesNotUsedSMSTokenByUserIdExists.doesNotUsedSMSTokenByUserIdExists("1234", 1L)).thenReturn(true);

        List<PasswordResetToken> tokens = new ArrayList<>();
        PasswordResetToken token = getNotExpiredToken(user, "1234", 1L, false);
        tokens.add(token);

        when(getPasswordResetTokensByUserId.getPasswordResetTokensByUserId(1L)).thenReturn(tokens);
        when(passwordEncoder.encode("1q2w3e4r")).thenReturn("qwerty");

        resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase("87011111111", "1q2w3e4r", "1234");

        assertTrue(token.getUsed());
    }


    @Test
    public void passwordResetTokenIsSaved_Success() throws UserNotFoundException, TokenNotFoundException, TokenExpiredException {
        User user = getUser("87011111111", "1q2w3e4r", 1L);

        when(doesUserByUsernameExists.doesUserExistByUsername("87011111111")).thenReturn(true);
        when(getUserByUsername.getUserByUsername("87011111111")).thenReturn(user);
        when(doesNotUsedSMSTokenByUserIdExists.doesNotUsedSMSTokenByUserIdExists("1234", 1L)).thenReturn(true);

        List<PasswordResetToken> tokens = new ArrayList<>();
        PasswordResetToken token = getNotExpiredToken(user, "1234", 1L, false);
        tokens.add(token);

        when(getPasswordResetTokensByUserId.getPasswordResetTokensByUserId(1L)).thenReturn(tokens);
        when(passwordEncoder.encode("1q2w3e4r")).thenReturn("qwerty");

        resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase("87011111111", "1q2w3e4r", "1234");

        verify(updatePasswordResetToken, times(1)).updatePasswordResetToken(token);
    }
}
