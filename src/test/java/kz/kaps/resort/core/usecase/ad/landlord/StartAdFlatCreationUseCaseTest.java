package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.HousingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static kz.kaps.resort.core.DefaultDomainCreator.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class StartAdFlatCreationUseCaseTest {

    @Mock
    private CreateAd createAd;
    @Mock
    private GetAdsByOwnerAndStatusAndHousingType getAdsByOwnerAndStatusAndHousingType;
    @InjectMocks
    private StartAdCreationUseCase startAdFlatCreationUseCase;

    @Captor
    private ArgumentCaptor<Ad> adCaptor;
    @Captor
    private ArgumentCaptor<Long> longCaptor;

    @Test
    public void errorWhenOwnerIdIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> startAdFlatCreationUseCase.startAdCreation(HousingType.FLAT, null)
        );
    }

    @Test
    public void errorWhenHousingTypeIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> startAdFlatCreationUseCase.startAdCreation(null, getUser())
        );
    }

    @Test
    public void seedAdCreated_WhenOldSeedDoesNotExist() {
        when(getAdsByOwnerAndStatusAndHousingType.getAdsByOwnerAndStatusAndHousingType(any(), any(), any())).thenReturn(Collections.emptyList());
        when(createAd.createAd(any())).thenReturn(geAd());
        startAdFlatCreationUseCase.startAdCreation(HousingType.FLAT, getUser());
        verify(createAd, times(1)).createAd(any());
    }

    @Test
    public void seedAdIsNotCreated_WhenOldSeedExists() {
        List<Ad> oldSeedAds = new ArrayList<>(1);
        oldSeedAds.add(geAd());
        when(getAdsByOwnerAndStatusAndHousingType.getAdsByOwnerAndStatusAndHousingType(any(), any(), any())).thenReturn(oldSeedAds);

        startAdFlatCreationUseCase.startAdCreation(HousingType.FLAT, getUser());
        verify(createAd, times(0)).createAd(any());
    }

    @Test
    public void seedAdIsCorrect_WhenOldSeedExists() {
        List<Ad> oldSeedAds = new ArrayList<>(1);
        oldSeedAds.add(geAdFlat());
        when(getAdsByOwnerAndStatusAndHousingType.getAdsByOwnerAndStatusAndHousingType(any(), any(), any())).thenReturn(oldSeedAds);

        Ad ad = startAdFlatCreationUseCase.startAdCreation(HousingType.FLAT, getUser());

        assertNotNull(ad);
        assertEquals(HousingType.FLAT, ad.getHousingType());
    }
}
