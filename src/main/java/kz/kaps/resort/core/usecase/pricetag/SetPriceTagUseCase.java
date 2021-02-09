package kz.kaps.resort.core.usecase.pricetag;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.PriceTag;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;

public class SetPriceTagUseCase {

    private GetPriceTagsByAdBetweenDates getPriceTagsBetweenDates;
    private UpdatePriceTag updatePriceTag;
    private DeletePriceTag deletePriceTag;
    private CreatePriceTag createPriceTag;

    public SetPriceTagUseCase(GetPriceTagsByAdBetweenDates getPriceTagsBetweenDates, UpdatePriceTag updatePriceTag,
                              DeletePriceTag deletePriceTag, CreatePriceTag createPriceTag){
        this.getPriceTagsBetweenDates = getPriceTagsBetweenDates;
        this.updatePriceTag = updatePriceTag;
        this.deletePriceTag = deletePriceTag;
        this.createPriceTag = createPriceTag;
    }

    public void setPriceTagUseCase(User user, Ad ad, LocalDate startDate, LocalDate endDate, int price)
            throws ForbiddenException {
        failsIfUserIsNull(user);
        failsIfAdIsNull(ad);
        failsIfStartDateIsNull(startDate);
        failsIfStartDateIsFromThePast(startDate);
        failsIfEndDateIsNull(endDate);
        failsIfEndDateIsBeforeStartDate(startDate, endDate);
        failsIfPriceIsNotPositive(price);
        failIfUserDoesNotOwnAd(user, ad);

        correctOverlappingPriceTags(ad, startDate, endDate);

        PriceTag priceTag = PriceTag.builder()
                .startDate(startDate)
                .endDate(endDate)
                .price(price)
                .build();

        createPriceTag.createPriceTag(priceTag, ad);
    }

    private void failsIfUserIsNull(User user){
        if(user == null) throw new IllegalArgumentException();
    }

    private void failsIfAdIsNull(Ad ad){
        if(ad == null) throw new IllegalArgumentException();
    }

    private void failsIfStartDateIsNull(LocalDate date){
        if(date == null) throw new IllegalArgumentException();
    }

    private void failsIfStartDateIsFromThePast(LocalDate date){
        LocalDate today = LocalDate.now();
        if(date.isBefore(today)) throw new IllegalArgumentException();
    }

    private void failsIfEndDateIsNull(LocalDate date){
        if(date == null) throw new IllegalArgumentException();
    }

    private void failsIfEndDateIsBeforeStartDate(LocalDate startDate, LocalDate endDate){
        if(endDate.isBefore(startDate)) throw new IllegalArgumentException();
    }

    private void failsIfPriceIsNotPositive(int price){
        if(price <= 0) throw new IllegalArgumentException();
    }

    private void failIfUserDoesNotOwnAd(User user, Ad ad) throws ForbiddenException {
        if(!ad.getOwner().getId().equals(user.getId()))
            throw new ForbiddenException();
    }


    private void correctOverlappingPriceTags(Ad ad, LocalDate startDate, LocalDate endDate) {
        Set<PriceTag> priceTags = getPriceTagsBetweenDates.getPriceTagsBetweenDates(ad.getId(), startDate, endDate);
        Iterator<PriceTag> iterator = priceTags.iterator();
        while (iterator.hasNext()){
            PriceTag nxtPT = iterator.next();
            LocalDate nxtPTStartDt = nxtPT.getStartDate();
            LocalDate nxtPTEndDt = nxtPT.getEndDate();

            if(
                    (nxtPTStartDt.isAfter(startDate) || nxtPTStartDt.isEqual(startDate)) &&
                            (nxtPTEndDt.isBefore(endDate) || nxtPTEndDt.isEqual(endDate))

            ) {
                deletePriceTag.deletePriceTag(nxtPT.getId());
            } else if(nxtPTStartDt.isBefore(startDate) && (nxtPTEndDt.isBefore(endDate) || nxtPTEndDt.isEqual(endDate))) {
                nxtPT.setEndDate(startDate.minusDays(1));
                updatePriceTag.updatePriceTag(nxtPT, ad);
            } else if((nxtPTStartDt.isAfter(startDate) || nxtPTStartDt.isEqual(startDate)) && nxtPTEndDt.isAfter(endDate)) {
                nxtPT.setStartDate(endDate.plusDays(1));
                updatePriceTag.updatePriceTag(nxtPT, ad);
            } else if(nxtPTStartDt.isBefore(startDate) && nxtPTEndDt.isAfter(endDate)) {
                nxtPT.setEndDate(startDate.minusDays(1));
                updatePriceTag.updatePriceTag(nxtPT, ad);

                PriceTag rightPT = PriceTag.builder()
                        .startDate(endDate.plusDays(1))
                        .endDate(nxtPTEndDt)
                        .price(nxtPT.getPrice())
                        .build();

                createPriceTag.createPriceTag(rightPT, ad);
            }
        }
    }

}
