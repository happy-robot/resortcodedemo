package kz.kaps.resort.core.usecase.pricetag;

import kz.kaps.resort.core.domain.PriceTag;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class GetPriceTagsByAdBetweenDatesUseCase {

    private GetPriceTagsByAdBetweenDates getPriceTagsBetweenDates;

    public GetPriceTagsByAdBetweenDatesUseCase(GetPriceTagsByAdBetweenDates getPriceTagsBetweenDates){
        this.getPriceTagsBetweenDates = getPriceTagsBetweenDates;
    }

    public Set<PriceTag> getPriceTagsByAdBetweenDatesUseCase(Long adId, LocalDate startDate, LocalDate endDate){
        failsIfAdIdIsNull(adId);
        failsIfStartDateIsNull(startDate);
        failsIfEndDateIsNull(endDate);
        return getPriceTagsBetweenDates.getPriceTagsBetweenDates(adId, startDate, endDate);
    }

    //startDate and endDate in format dd.MM.yyyy
    public Set<PriceTag> getPriceTagsByAdBetweenDatesUseCase(Long adId, String startDate, String endDate){
        //todo check start and end date for cases like 30.30.2020
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDateLD = LocalDate.parse(startDate, formatter);
        LocalDate endDateLD = LocalDate.parse(endDate, formatter);
        return getPriceTagsByAdBetweenDatesUseCase(adId, startDateLD, endDateLD);
    }

    private void failsIfAdIdIsNull(Long adId){
        if(adId == null) throw new IllegalArgumentException();
    }

    private void failsIfStartDateIsNull(LocalDate startDate){
        if(startDate == null) throw new IllegalArgumentException();
    }

    private void failsIfEndDateIsNull(LocalDate endDate){
        if(endDate == null) throw new IllegalArgumentException();
    }

}
