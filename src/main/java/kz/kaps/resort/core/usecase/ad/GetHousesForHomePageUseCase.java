package kz.kaps.resort.core.usecase.ad;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.HousingType;

import java.util.List;

public class GetHousesForHomePageUseCase {

    private GetAdsByHousingType getAdsByHousingType;

    public GetHousesForHomePageUseCase(GetAdsByHousingType getAdsByHousingType){
        this.getAdsByHousingType = getAdsByHousingType;
    }

    public List<Ad> getHousesForHomePage(){
        List<Ad> ads = getAdsByHousingType.getAdsByHousingType(HousingType.HOUSE);
        return ads;
    }


}
