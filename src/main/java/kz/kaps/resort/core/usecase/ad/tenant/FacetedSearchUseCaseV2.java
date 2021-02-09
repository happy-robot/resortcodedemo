package kz.kaps.resort.core.usecase.ad.tenant;

import kz.kaps.resort.configuration.ResourceHandlerConfig;
import kz.kaps.resort.core.dictionaries.GetAllLocalities;
import kz.kaps.resort.core.domain.*;
import kz.kaps.resort.core.usecase.utils.LocaleUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kz.kaps.resort.core.usecase.utils.LocaleUtils.*;


public class FacetedSearchUseCaseV2 {

    private final String FILTER_FACILITIES = "facilities";
    private final String FILTER_TECHNICS = "technics";

    private FacetedSearchByQuery facetedSearchByQuery;
    private GetAllLocalities getLocalities;
    private MessageSource messageSource;

    public FacetedSearchUseCaseV2(FacetedSearchByQuery facetedSearchByQuery, GetAllLocalities getLocalities, MessageSource messageSource) {
        this.facetedSearchByQuery = facetedSearchByQuery;
        this.getLocalities = getLocalities;
        this.messageSource = messageSource;
    }

    public SearchResponseDto facetedSearch(String query, Pageable pageable) {
        FacetedSearchFormDto searchForm = new FacetedSearchFormDto(query);
        searchForm.setStatus(AdStatusEnum.PUBLISHED);

        Page<Ad> page = facetedSearchByQuery.facetedSearchByQuery(searchForm, pageable);
        List<Ad> ads = page.getContent();
        Page<AdForSearch> page2 = new PageImpl<>(
                ads.stream().map(s -> {
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
                }).collect(Collectors.toList()),
                pageable, page.getTotalElements()
        );
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        searchResponseDto.setCards(page2);
        searchResponseDto.setFilters(generateFilterStructure(searchForm));
        searchResponseDto.setStartPrice(searchForm.getStartPrice());
        searchResponseDto.setEndPrice(searchForm.getEndPrice());
        searchResponseDto.setStartRoomNum(searchForm.getStartRoomNum());
        searchResponseDto.setEndRoomNum(searchForm.getEndRoomNum());
        return searchResponseDto;
    }

    private List<FilterDto> generateFilterStructure(FacetedSearchFormDto searchForm) {
        List<FilterDto> filters = new ArrayList<>();

        filters.add(getLocalityFilter(searchForm));
        filters.add(getHousingTypeFilter(searchForm));
        filters.add(getFacilitiesFilter(searchForm));
        filters.add(getTechnicsFilter(searchForm));

        return filters;
    }

    private FilterDto getLocalityFilter(FacetedSearchFormDto searchForm) {
        FilterDto filterDto = FilterDto.builder()
                .active(false)
                .id(FacetedSearchFormDto.ATTRIBUTE_LOCALITY_IDS)
                .multiselect(true)
                .title(messageSource.getMessage(MSG_CODE_COMMON_LOCALITY, null, LocaleUtils.getLocale()))
                .rows(getLocalities.getAllLocalities().stream().map(l -> FilterRowDto.builder()
                        .active(searchForm.containsLocalityId(l.getId()))
                        .hide(false)
                        .title(l.getName())
                        .id(":" + FacetedSearchFormDto.ATTRIBUTE_LOCALITY_IDS + ":" + l.getId().toString())
                        .build()
                ).collect(Collectors.toList()))
                .build();

        filterDto.setActive(filterDto.getRows().stream().anyMatch(r -> r.getActive() == true));
        return filterDto;
    }

    private FilterDto getHousingTypeFilter(FacetedSearchFormDto searchForm) {
        FilterDto filterDto = FilterDto.builder()
                .active(false)
                .id(FacetedSearchFormDto.ATTRIBUTE_HOUSING_TYPE)
                .multiselect(true)
                .title(messageSource.getMessage(MSG_CODE_COMMON_HOUSING_TYPE, null, LocaleUtils.getLocale()))
                .rows(
                        Arrays.stream(HousingType.values()).map(s -> FilterRowDto.builder()
                                .active(searchForm.containsHousingType(s))
                                .hide(false)
                                .title(s.getName())
                                .id(":" + FacetedSearchFormDto.ATTRIBUTE_HOUSING_TYPE + ":" + s.toString())
                                .build()).collect(Collectors.toList())
                )
                .build();

        filterDto.setActive(filterDto.getRows().stream().anyMatch(r -> r.getActive() == true));
        return filterDto;
    }

    private FilterDto getFacilitiesFilter(FacetedSearchFormDto searchForm) {
        FilterDto filterDto = FilterDto.builder()
                .active(false)
                .id(FILTER_FACILITIES)
                .multiselect(true)
                .rows(new ArrayList<>())
                .title(messageSource.getMessage(MSG_CODE_COMMON_FACILITIES, null, LocaleUtils.getLocale()))
                .build();

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getHotWater() != null ? searchForm.getHotWater() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_HOT_WATER, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_HOT_WATER + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getColdWater() != null ? searchForm.getColdWater() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_COLD_WATER, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_COLD_WATER + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getSauna() != null ? searchForm.getSauna() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_SAUNA, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_SAUNA + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getBathhouse() != null ? searchForm.getBathhouse() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_BATHHOUSE, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_BATHHOUSE + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getAlcove() != null ? searchForm.getAlcove() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_ALCOVE, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_ALCOVE + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getTapchan() != null ? searchForm.getTapchan() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_TAPCHAN, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_TAPCHAN + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getBrazier() != null ? searchForm.getBrazier() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_BRAZIER, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_BRAZIER + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getKazan() != null ? searchForm.getKazan() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_KAZAN, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_KAZAN + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getSummerKitchen() != null ? searchForm.getSummerKitchen() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_SUMMER_KITCHEN, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_SUMMER_KITCHEN + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getPlayground() != null ? searchForm.getPlayground() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_PLAYGROUND, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_PLAYGROUND + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getBicycles() != null ? searchForm.getBicycles() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_BICYCLES, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_BYCICLES + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getQuadBikes() != null ? searchForm.getQuadBikes() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_QUAD_BIKES, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_QUAD_BIKES + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getBilliards() != null ? searchForm.getBilliards() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_BILLIARDS, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_BILLIARDS + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getSwimmingPool() != null ? searchForm.getSwimmingPool() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_SWIMMING_POOL, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_SWIMMING_POOL + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getCook() != null ? searchForm.getCook() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_COOK, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_COOK + ":true")
                .build());

        filterDto.setActive(filterDto.getRows().stream().anyMatch(r -> r.getActive() == true));
        return filterDto;
    }


    private FilterDto getTechnicsFilter(FacetedSearchFormDto searchForm) {
        FilterDto filterDto = FilterDto.builder()
                .active(false)
                .id(FILTER_TECHNICS)
                .multiselect(true)
                .rows(new ArrayList<>())
                .title(messageSource.getMessage(MSG_CODE_COMMON_TECHNICS, null, LocaleUtils.getLocale()))
                .build();

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getStove() != null ? searchForm.getStove() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_STOVE, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_STOVE + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getFridge() != null ? searchForm.getFridge() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_FRIDGE, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_FRIDGE + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getMicrowave() != null ? searchForm.getMicrowave() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_MICROWAVE, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_MICROWAVE + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getWifi() != null ? searchForm.getWifi() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_WIFI, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_WIFI + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getTv() != null ? searchForm.getTv() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_TV, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_TV + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getWasher() != null ? searchForm.getWasher() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_WASHER, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_WASHER + ":true")
                .build());

        filterDto.getRows().add(FilterRowDto.builder()
                .active(searchForm.getConditioner() != null ? searchForm.getConditioner() : false)
                .hide(false)
                .title(messageSource.getMessage(MSG_CODE_COMMON_CONDITIONER, null, LocaleUtils.getLocale()))
                .id(":" + FacetedSearchFormDto.ATTRIBUTE_CONDITIONER + ":true")
                .build());

        filterDto.setActive(filterDto.getRows().stream().anyMatch(r -> r.getActive() == true));
        return filterDto;
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
