package kz.kaps.resort.core.usecase.ad.landlord.getlandlordads;

import kz.kaps.resort.configuration.ResourceHandlerConfig;
import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.HousingType;
import kz.kaps.resort.core.usecase.ad.landlord.GetAdsByOwnerAndStatus;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.core.usecase.utils.LocaleUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class GetLandlordAdsUseCase {

    private GetAdsByOwnerAndStatus getAdsByOwnerAndStatus;
    private ModelMapper modelMapper;
    private MessageSource messageSource;

    public GetLandlordAdsUseCase(GetAdsByOwnerAndStatus getAdsByOwnerAndStatus, ModelMapper modelMapper, MessageSource messageSource){
        this.getAdsByOwnerAndStatus = getAdsByOwnerAndStatus;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    public List<AdShortDto> getAdsByOwnerAndStatus(String ownerUsername, AdStatusEnum status) throws ForbiddenException {
        failsIfOwnerUsernameIsEmpty(ownerUsername);


        if(status == null) status = AdStatusEnum.PUBLISHED;

        List<AdShortProjection> adShortProjections = getAdsByOwnerAndStatus.getAdsByOwnerAndStatus(ownerUsername, status);
        return adShortProjections.stream().map(a -> {
            AdShortDto adShortDto = modelMapper.map(a, AdShortDto.class);
            adShortDto.setImageUrlForMediumSize(ResourceHandlerConfig.MEDIUM_IMAGES_URL + a.getImageFileNameWithExt());
            adShortDto.setHeader(getLocalizedHeader(a));
            return adShortDto;
        })
                .collect(Collectors.toList());
    }

    private void failsIfOwnerUsernameIsEmpty(String ownerUsername) throws ForbiddenException {
        if(StringUtils.isEmpty(ownerUsername)) throw new ForbiddenException();
    }

    private String getLocalizedHeader(AdShortProjection ad) {
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
}
