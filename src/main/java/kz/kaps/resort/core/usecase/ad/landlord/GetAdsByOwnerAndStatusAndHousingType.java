package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.HousingType;

import java.util.List;

public interface GetAdsByOwnerAndStatusAndHousingType {

    List<Ad> getAdsByOwnerAndStatusAndHousingType(Long ownerId, AdStatusEnum status, HousingType housingType);

}
