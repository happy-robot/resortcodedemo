package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.usecase.ad.DoesAdExist;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ActivateAdUseCaseTest {

    @Mock
    private DoesAdExist doesAdExist;
    @Mock
    private UpdateAdStatus updateAdStatus;
    @Mock
    private DoesUserOwnAd doesUserOwnAd;

    @Captor
    private ArgumentCaptor<AdStatusEnum> adStatusEnumCaptor;
    @Captor
    private ArgumentCaptor<Long> longCaptor;

    @InjectMocks
    private ActivateAdUseCase activateAdUseCase;

    @Test
    public void errorWhenUsernameIsEmpty(){
        assertThatExceptionOfType(ForbiddenException.class).isThrownBy(
                () -> activateAdUseCase.activateAd(1L, null)
        );
    }

    @Test
    public void errorWhenAdIdIsNull(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> activateAdUseCase.activateAd(null, "username")
        );
    }

    @Test
    public void errorWhenAdDoesNotExist(){
        when(doesAdExist.doesAdExist(any())).thenReturn(false);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> activateAdUseCase.activateAd(1L, "username")
        );
    }

    @Test
    public void errorWhenUserDoesNotOwnAd(){
        when(doesAdExist.doesAdExist(any())).thenReturn(true);
        when(doesUserOwnAd.doesUserOwnAd(any(), any())).thenReturn(false);
        assertThatExceptionOfType(ForbiddenException.class).isThrownBy(
                () -> activateAdUseCase.activateAd(1L, "username")
        );
    }

    @Test
    public void isActivated() throws ForbiddenException {
        when(doesAdExist.doesAdExist(any())).thenReturn(true);
        when(doesUserOwnAd.doesUserOwnAd(any(), any())).thenReturn(true);
        activateAdUseCase.activateAd(1L, "username");

        verify(updateAdStatus, times(1)).updateAdStatus(any(), any());
        verify(updateAdStatus).updateAdStatus(longCaptor.capture(), adStatusEnumCaptor.capture());
        assertEquals(AdStatusEnum.PUBLISHED, adStatusEnumCaptor.getValue());
    }
}
