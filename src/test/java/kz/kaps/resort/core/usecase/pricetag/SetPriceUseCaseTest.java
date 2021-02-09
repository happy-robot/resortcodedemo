package kz.kaps.resort.core.usecase.pricetag;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
public class SetPriceUseCaseTest {

    GetPriceTagsByAdBetweenDates getPriceTagsBetweenDates = mock(GetPriceTagsByAdBetweenDates.class);
    UpdatePriceTag updatePriceTag = mock(UpdatePriceTag.class);
    DeletePriceTag deletePriceTag = mock(DeletePriceTag.class);
    CreatePriceTag createPriceTag = mock(CreatePriceTag.class);
    SetPriceTagUseCase setPriceUseCase = new SetPriceTagUseCase(getPriceTagsBetweenDates, updatePriceTag, deletePriceTag,
            createPriceTag);

    @Test
    public void errorWhenUserIsEmpty(){
        User user = null;
        Ad ad = getAd();
        LocalDate startDate = getTomorrow();
        LocalDate endDate = getOneDayAfterTomorrow();
        int price = 1000;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> setPriceUseCase.setPriceTagUseCase(user, ad, startDate, endDate, price)
        );
    }

    private User getUser1(){
        User user = new User("username", "password");
        user.setId(1L);
        return user;
    }

    private User getUser2(){
        User user = new User("username", "password");
        user.setId(2L);
        return user;
    }

    private LocalDate getYesterday(){
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        return yesterday;
    }

    private LocalDate getTomorrow(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        return tomorrow;
    }

    private LocalDate getOneDayAfterTomorrow(){
        LocalDate tomorrow = getTomorrow();
        return tomorrow.plusDays(1);
    }

    private Ad getAd(){
        Ad ad = Ad.builder()
                .build();
        return ad;
    }

    @Test
    public void errorWhenAdIsEmpty(){
        User user = getUser1();
        Ad ad = null;
        LocalDate startDate = getTomorrow();
        LocalDate endDate = getOneDayAfterTomorrow();
        int price = 1000;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> setPriceUseCase.setPriceTagUseCase(user, ad, startDate, endDate, price)
        );
    }

    @Test
    public void errorWhenStartDateIsEmpty(){
        User user = getUser1();
        Ad ad = getAd();
        LocalDate startDate = null;
        LocalDate endDate = getOneDayAfterTomorrow();
        int price = 1000;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> setPriceUseCase.setPriceTagUseCase(user, ad, startDate, endDate, price)
        );
    }

    @Test
    public void errorWhenStartDateIsFromThePast(){
        User user = getUser1();
        Ad ad = getAd();
        LocalDate startDate = getYesterday();
        LocalDate endDate = getOneDayAfterTomorrow();
        int price = 1000;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> setPriceUseCase.setPriceTagUseCase(user, ad, startDate, endDate, price)
        );
    }

    @Test
    public void errorWhenEndDateIsEmpty(){
        User user = getUser1();
        Ad ad = getAd();
        LocalDate startDate = getTomorrow();
        LocalDate endDate = null;
        int price = 1000;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> setPriceUseCase.setPriceTagUseCase(user, ad, startDate, endDate, price)
        );
    }

    @Test
    public void errorWhenEndDateIsBeforeStartDate(){
        User user = getUser1();
        Ad ad = getAd();

        LocalDate startDate = getOneDayAfterTomorrow();
        LocalDate endDate = getTomorrow();

        int price = 1000;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> setPriceUseCase.setPriceTagUseCase(user, ad, startDate, endDate, price)
        );
    }

    @Test
    public void errorWhenPriceIsNotPositive(){
        User user = getUser1();
        Ad ad = getAd();
        LocalDate startDate = getTomorrow();
        LocalDate endDate = getOneDayAfterTomorrow();
        int price = -1000;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> setPriceUseCase.setPriceTagUseCase(user, ad, startDate, endDate, price)
        );
    }

    @Test
    public void errorWhenUserDoesNotOwnAd(){
        User user = getUser1();

        Ad ad = getAd();
        ad.setOwner(getUser2());

        LocalDate startDate = getTomorrow();
        LocalDate endDate = getOneDayAfterTomorrow();

        int price = 1000;

        assertThatExceptionOfType(ForbiddenException.class).isThrownBy(
                () -> setPriceUseCase.setPriceTagUseCase(user, ad, startDate, endDate, price)
        );
    }

}
