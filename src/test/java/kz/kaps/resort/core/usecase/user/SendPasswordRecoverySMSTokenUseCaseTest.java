package kz.kaps.resort.core.usecase.user;

import kz.kaps.resort.core.communication.SMSService;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class SendPasswordRecoverySMSTokenUseCaseTest {

    @Mock
    private GetUserByUsername getUserByUsername;

    @Mock
    private DoesUserByUsernameExists doesUserByUsernameExists;

    @Mock
    private CreatePasswordResetToken createPasswordResetToken;

    @Mock
    private SMSService SMSService;

    @InjectMocks
    private SendPasswordRecoverySMSTokenUseCase sendPasswordRecoverySMSTokenUseCase;


    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void errorWhenUsernameIsEmpty(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> sendPasswordRecoverySMSTokenUseCase.sendPasswordRecoverySMSToken(null));
    }

    @Test
    public void errorWhenUserDoesNotExist(){
        when(doesUserByUsernameExists.doesUserExistByUsername(anyString())).thenReturn(false);
        assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> sendPasswordRecoverySMSTokenUseCase.sendPasswordRecoverySMSToken("87011111111"));
    }

    @Test
    public void passwordResetTokenIsCreated_Success() throws UserNotFoundException {
        User user = new User("87011111111", "1q2w3e4r");
        user.setId(1L);

        when(doesUserByUsernameExists.doesUserExistByUsername("87011111111")).thenReturn(true);
        when(getUserByUsername.getUserByUsername("87011111111")).thenReturn(user);
        sendPasswordRecoverySMSTokenUseCase.sendPasswordRecoverySMSToken("87011111111");
        verify(createPasswordResetToken, times(1)).createPasswordResetToken(any());
    }

    @Test
    public void smsTokenIsSend_Success() throws UserNotFoundException {
        User user = new User("87011111111", "1q2w3e4r");
        user.setId(1L);

        when(doesUserByUsernameExists.doesUserExistByUsername("87011111111")).thenReturn(true);
        when(getUserByUsername.getUserByUsername("87011111111")).thenReturn(user);
        sendPasswordRecoverySMSTokenUseCase.sendPasswordRecoverySMSToken("87011111111");
        verify(SMSService, times(1)).sendSMS(anyString(), anyString());
    }

}
