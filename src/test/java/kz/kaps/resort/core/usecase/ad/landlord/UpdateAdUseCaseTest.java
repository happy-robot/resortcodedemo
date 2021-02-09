package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.ad.GetAd;
import kz.kaps.resort.core.usecase.ad.UpdateAd;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static kz.kaps.resort.core.DefaultDomainCreator.*;
import static kz.kaps.resort.core.DefaultDomainCreator.geAdFlat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UpdateAdUseCaseTest {

    @Mock
    private GetUserByUsername getUserByUsername;
    @Mock
    private UpdateAd updateAd;
    @Mock
    private GetAd getAd;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UpdateAdUseCase updateAdUseCase;


    @Test
    public void errorWhenAdIsNull(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> updateAdUseCase.updateAd(null, "username")
        );
    }

    @Test
    public void errorWhenAdIdIsNull(){
        Ad ad = geAdFlat();
        ad.setId(null);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> updateAdUseCase.updateAd(geAdFlat(), "username")
        );
    }

    @Test
    public void errorWhenOwneruserNameIsEmpty(){
        assertThatExceptionOfType(ForbiddenException.class).isThrownBy(
                () -> updateAdUseCase.updateAd(geAdFlat(), "")
        );
    }

    @Test
    public void errorWhenOldAdDoesNotExist(){
        when(getAd.getAd(any())).thenReturn(null);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> updateAdUseCase.updateAd(geAdFlat(), "username")
        );
    }

    @Test
    public void errorWhenUserDoesNotExist(){
        when(getUserByUsername.getUserByUsername(any())).thenReturn(null);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> updateAdUseCase.updateAd(geAdFlat(), "username")
        );
    }

    @Test
    public void errorWhenUserDoesNotOwnAd(){
        when(getUserByUsername.getUserByUsername(any())).thenReturn(getUser());
        Ad ad = geAdFlat();
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("username2");
        ad.setOwner(user2);
        when(getAd.getAd(any())).thenReturn(ad);
        assertThatExceptionOfType(ForbiddenException.class).isThrownBy(
                () -> updateAdUseCase.updateAd(geAdFlat(), "username")
        );
    }

    @Test
    public void adIsUpdated() throws ForbiddenException {
        Ad ad = geAdFlat();
        ad.setOwner(getUser());

        when(getUserByUsername.getUserByUsername(any())).thenReturn(getUser());
        when(modelMapper.map(any(), any())).thenReturn(ad);
        when(getAd.getAd(any())).thenReturn(ad);
        updateAdUseCase.updateAd(geAdFlat(), "username");
        verify(updateAd, times(1)).updateAd(any());
    }

}
