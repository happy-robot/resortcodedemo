package kz.kaps.resort.core.usecase.ad.tenant;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.usecase.utils.LocaleUtils;
import org.springframework.context.MessageSource;


import static kz.kaps.resort.core.usecase.utils.LocaleUtils.*;

public class ViewAdUseCase {

    private GetAd getAd;
    private MessageSource messageSource;

    public ViewAdUseCase(GetAd getAd, MessageSource messageSource) {
        this.getAd = getAd;
        this.messageSource = messageSource;
    }

    public AdView viewAd(Long adId) {
        Ad ad = getAd.getAdView(adId);
        AdView adView = new AdView();
        adView.setAd(ad);
        adView.setHeader(getHeader(ad));
        adView.setWeekdayPrice(getWeekdayPrice(ad));
        adView.setHolidayPrice(getHolidayPrice(ad));

        return adView;
    }

    private String getHeader(Ad ad) {
        String header = null;
        switch (ad.getHousingType()) {
            case FLAT:
                Object[] flatParams = {ad.getRoomNum(), ad.getArea()};
                header = messageSource.getMessage(MSG_CODE_FLAT_HEADER, flatParams, LocaleUtils.getLocale());
                break;
            case HOUSE:
                Object[] houseParams = {ad.getFloorNum(), ad.getArea()};
                header = messageSource.getMessage(MSG_CODE_HOUSE_HEADER, houseParams, LocaleUtils.getLocale());
                break;
            case COTTAGE:
                Object[] cottageParams = {ad.getFloorNum(), ad.getArea()};
                header = messageSource.getMessage(MSG_CODE_COTTAGE_HEADER, cottageParams, LocaleUtils.getLocale());
                break;
            case SUMMER_HOUSE:
                if(ad.getSummerHousesCount() != null) {
                    Object[] summerHousesParams = {ad.getSummerHousesCount()};
                    header = messageSource.getMessage(MSG_CODE_SUMMER_HOUSES_HEADER, summerHousesParams, LocaleUtils.getLocale());
                } else {
                    header = messageSource.getMessage(MSG_CODE_PLURAL_SUMMER_HOUSES, null, LocaleUtils.getLocale());
                }
                break;
            default:
                header = null;
        }
        return header;
    }

    private String getWeekdayPrice(Ad ad) {
        if(ad.getWeekdayPricePerDay() == null) return null;

        String price = null;
        if(ad.getIsPricePerPerson() != null && ad.getIsPricePerPerson()) {
            price = ad.getWeekdayPricePerDay().toString() + " " + messageSource.getMessage(MSG_CODE_COMMON_PRICE_PER_DAY_PER_PERSON_TG, null, LocaleUtils.getLocale());
        } else {
            price = ad.getWeekdayPricePerDay().toString() + " " + messageSource.getMessage(MSG_CODE_COMMON_PRICE_PER_DAY_TG, null, LocaleUtils.getLocale());
        }
        return price;
    }

    private String getHolidayPrice(Ad ad) {
        if(ad.getWeekdayPricePerDay() == null) return null;

        String price = null;
        if(ad.getIsPricePerPerson() != null && ad.getIsPricePerPerson()) {
            price = ad.getHolidayPricePerDay().toString() + " " + messageSource.getMessage(MSG_CODE_COMMON_PRICE_PER_DAY_PER_PERSON_TG, null, LocaleUtils.getLocale());
        } else {
            price = ad.getHolidayPricePerDay().toString() + " " + messageSource.getMessage(MSG_CODE_COMMON_PRICE_PER_DAY_TG, null, LocaleUtils.getLocale());
        }
        return price;
    }

    public interface GetAd {
        Ad getAdView(Long adId);
    }
}
