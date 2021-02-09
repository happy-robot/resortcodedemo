package kz.kaps.resort.core.usecase.ad;


import kz.kaps.resort.core.domain.Ad;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GetAdUseCaseTest {

    GetAd getAd = mock(GetAd.class);
    DoesAdExist doesAdExist = mock(DoesAdExist.class);
    UpdateAd updateAd = mock(UpdateAd.class);

    GetAdUseCase getAdUseCase = new GetAdUseCase(getAd, doesAdExist, updateAd);

    @Test
    public void viewsNumRecountSuccess() {
        Ad ad = Ad.builder()
                .header("Header1")
                .description("Description1")
                .viewsNum(1)
                .build();
        when(getAd.getAd(any())).thenReturn(ad);
        when(doesAdExist.doesAdExist(any())).thenReturn(true);

        getAdUseCase.getAd(1L);

        assertThat(ad.getViewsNum()).isEqualTo(2);
    }

    @Test
    public void errorWhenAdIsNotFound(){
        when(getAd.getAd(any())).thenReturn(null);
        when(doesAdExist.doesAdExist(any())).thenReturn(false);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> getAdUseCase.getAd(1L));
    }


}
