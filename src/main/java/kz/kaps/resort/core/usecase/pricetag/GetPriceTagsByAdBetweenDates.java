package kz.kaps.resort.core.usecase.pricetag;

import kz.kaps.resort.core.domain.PriceTag;

import java.time.LocalDate;
import java.util.Set;

public interface GetPriceTagsByAdBetweenDates {

    Set<PriceTag> getPriceTagsBetweenDates(Long adId, LocalDate startDate, LocalDate endDate);

}
