package kz.kaps.resort.core.usecase.ad.tenant;

import kz.kaps.resort.configuration.ResourceHandlerConfig;
import kz.kaps.resort.core.domain.*;
import kz.kaps.resort.core.usecase.utils.LocaleUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kz.kaps.resort.core.usecase.utils.LocaleUtils.MSG_CODE_COMMON_TG;

public class GetHotAdsUseCase {

    private FacetedSearchByQuery facetedSearchByQuery;
    private MessageSource messageSource;

    public GetHotAdsUseCase(FacetedSearchByQuery facetedSearchByQuery, MessageSource messageSource) {
        this.facetedSearchByQuery = facetedSearchByQuery;
        this.messageSource = messageSource;
    }

    public List<AdForSearch> getHotAdsUseCase() {
        //todo временное решение, заменить на список горячих предложений (здесь просто скопировал из фасетного поиска)
        FacetedSearchFormDto searchForm = new FacetedSearchFormDto();
        searchForm.setStatus(AdStatusEnum.PUBLISHED);

        PageRequest pageable = PageRequest.of(0, 10);
        Page<Ad> page = facetedSearchByQuery.facetedSearchByQuery(searchForm, pageable);
        List<Ad> ads = page.getContent();
        List<AdForSearch> adsForSearch = ads.stream().map(s -> {
            AdForSearch adForSearch = new AdForSearch(s);
            adForSearch.setHeader(getLocalizedHeader(s));
            adForSearch.setPrices(getLocalizedPrices(s));
            if(s.getLocality() != null && s.getLocality().getNameRu() != null)
                adForSearch.setLocalityName(LocaleUtils.getLocalized(s.getLocality().getNameRu(), s.getLocality().getNameEn()));
            Optional<Image> mainImage = s.getImages().stream().filter(i -> i.getOrderNumber() == 1).findFirst();
            if(mainImage.isPresent()) {
                adForSearch.setImageUrlForMediumSize(ResourceHandlerConfig.MEDIUM_IMAGES_URL + mainImage.get().getFileNameWithExt());
            }
            return adForSearch;
        }).collect(Collectors.toList());

        return adsForSearch;
    }

    private String getLocalizedHeader(Ad ad) {
        HousingType housingType = ad.getHousingType();
        if(housingType == HousingType.COTTAGE) {
            Object[] objects = {ad.getFloorNum(), ad.getRoomNum()};
            return messageSource.getMessage("faceted.search.ad.cottage.header", objects, LocaleUtils.getLocale());
        } else if(housingType == HousingType.FLAT) {
            Object[] objects = {ad.getRoomNum(), ad.getArea()};
            return messageSource.getMessage("faceted.search.ad.flat.header", objects, LocaleUtils.getLocale());
        } else if(housingType == HousingType.SUMMER_HOUSE) {
            Object[] objects = {ad.getRoomNum(), ad.getArea()};
            return messageSource.getMessage("faceted.search.ad.summer_house.header", objects, LocaleUtils.getLocale());
        } else if(housingType == HousingType.HOUSE) {
            Object[] objects = {ad.getRoomNum(), ad.getArea()};
            return messageSource.getMessage("faceted.search.ad.house.header", objects, LocaleUtils.getLocale());
        }

        return "";
    }

    private String getLocalizedPrices(Ad ad) {
        if(ad.getWeekdayPricePerDay() != null && ad.getHolidayPricePerDay() != null)
            return ad.getWeekdayPricePerDay() + " - " + ad.getHolidayPricePerDay() + messageSource.getMessage(MSG_CODE_COMMON_TG, null, LocaleUtils.getLocale());
        else if(ad.getWeekdayPricePerDay() != null)
            return ad.getWeekdayPricePerDay() + messageSource.getMessage(MSG_CODE_COMMON_TG, null, LocaleUtils.getLocale());
        else if(ad.getHolidayPricePerDay() != null)
            return ad.getHolidayPricePerDay() + messageSource.getMessage(MSG_CODE_COMMON_TG, null, LocaleUtils.getLocale());
        else return "";
    }
}
