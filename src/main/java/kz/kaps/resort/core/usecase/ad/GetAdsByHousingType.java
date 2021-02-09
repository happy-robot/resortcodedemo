package kz.kaps.resort.core.usecase.ad;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.HousingType;

import java.util.List;

public interface GetAdsByHousingType {

    List<Ad> getAdsByHousingType(HousingType housingType);

}
