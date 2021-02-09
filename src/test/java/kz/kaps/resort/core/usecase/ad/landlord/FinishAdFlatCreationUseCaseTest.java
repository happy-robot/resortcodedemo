package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.ad.GetAd;
import kz.kaps.resort.core.usecase.ad.UpdateAd;
import kz.kaps.resort.core.usecase.image.DoesImageExistByAdId;
import kz.kaps.resort.core.usecase.exception.*;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static kz.kaps.resort.core.DefaultDomainCreator.*;
import static kz.kaps.resort.core.DefaultDomainCreator.geAdFlat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class FinishAdFlatCreationUseCaseTest {

    @Mock
    private GetUserByUsername getUserByUsername;
    @Mock
    private UpdateAd updateAd;
    @Mock
    private GetAd getAd;
    @Mock
    private DoesImageExistByAdId doesImageExistByAdId;
    @Mock
    private DoesUserOwnAd doesUserOwnAd;
    @Mock
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Ad> adCaptor;

    @InjectMocks
    private FinishAdCreationUseCase finishAdCreationUseCase;

    @Test
    public void errorWhenAdIsNull(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> finishAdCreationUseCase.finishAdCreationUseCase(null, "username")
        );
    }
    @Test
    public void errorWhenAdIdIsNull(){
        Ad ad = geAdFlat();
        ad.setId(null);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> finishAdCreationUseCase.finishAdCreationUseCase(ad, "username")
        );
    }

    @Test
    public void errorWhenOwnerUsernameIsEmpty(){
        assertThatExceptionOfType(ForbiddenException.class).isThrownBy(
                () -> finishAdCreationUseCase.finishAdCreationUseCase(geAdFlat(), null)
        );
    }

    @Test
    public void errorWhenOwnerDoesNotExist(){
        when(getUserByUsername.getUserByUsername("username")).thenReturn(null);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> finishAdCreationUseCase.finishAdCreationUseCase(geAdFlat(), "username")
        );
    }

    @Test
    public void errorWhenAdisNotSeed(){
        Ad ad = geAdFlat();
        ad.setStatus(AdStatusEnum.DRAFT);
        when(getAd.getAd(any())).thenReturn(ad);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> finishAdCreationUseCase.finishAdCreationUseCase(geAdFlat(), "username")
        );
    }

    @Test
    public void errorWhenOwnerDoesNotOwnSeedAd(){
        when(getUserByUsername.getUserByUsername("username")).thenReturn(getUser());
        User owner2 = new User(2L, "username2", "password2");
        Ad ad = geAdFlat();
        ad.setOwner(owner2);
        ad.setStatus(AdStatusEnum.SEED);
        when(getAd.getAd(1L)).thenReturn(ad);
        assertThatExceptionOfType(ForbiddenException.class).isThrownBy(
                () -> finishAdCreationUseCase.finishAdCreationUseCase(geAdFlat(), "username")
        );
    }

    @Test
    public void errorWneImgDoesNotAdded() throws ForbiddenException, AdAlreadyExistsException, ImagesDoNotExistException {
        when(getUserByUsername.getUserByUsername("username")).thenReturn(getUser());
        when(doesUserOwnAd.doesUserOwnAd(any(), any())).thenReturn(true);
        when(doesImageExistByAdId.doesImageExistById(any())).thenReturn(false);
        Ad ad = geAdFlat();
        ad.setOwner(getUser());
        ad.setStatus(AdStatusEnum.SEED);
        when(getAd.getAd(1L)).thenReturn(ad);

        assertThatExceptionOfType(ImagesDoNotExistException.class).isThrownBy(
                () -> finishAdCreationUseCase.finishAdCreationUseCase(geAdFlat(), "username")
        );
    }

    @Test
    public void adIsUpdated() throws ForbiddenException, AdAlreadyExistsException, ImagesDoNotExistException {
        Ad ad = geAdFlat();
        ad.setOwner(getUser());
        ad.setStatus(AdStatusEnum.SEED);

        when(getUserByUsername.getUserByUsername("username")).thenReturn(getUser());
        when(doesUserOwnAd.doesUserOwnAd(any(), any())).thenReturn(true);
        when(doesImageExistByAdId.doesImageExistById(any())).thenReturn(true);
        when(modelMapper.map(any(), any())).thenReturn(ad);
        when(getAd.getAd(1L)).thenReturn(ad);

        finishAdCreationUseCase.finishAdCreationUseCase(geAdFlat(), "username");
        verify(updateAd, times(1)).updateAd(any());
    }

    @Test
    public void adChangedStatusToDraft() throws ForbiddenException, AdAlreadyExistsException, ImagesDoNotExistException {
        when(getUserByUsername.getUserByUsername("username")).thenReturn(getUser());
        when(doesUserOwnAd.doesUserOwnAd(any(), any())).thenReturn(true);
        when(doesImageExistByAdId.doesImageExistById(any())).thenReturn(true);
        Ad ad = geAdFlat();
        ad.setOwner(getUser());
        ad.setStatus(AdStatusEnum.SEED);
        when(getAd.getAd(1L)).thenReturn(ad);
        when(modelMapper.map(any(), any())).thenReturn(ad);

        finishAdCreationUseCase.finishAdCreationUseCase(geAdFlat(), "username");
        verify(updateAd).updateAd(adCaptor.capture());
    }
}
