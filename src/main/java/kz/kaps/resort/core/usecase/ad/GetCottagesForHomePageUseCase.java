package kz.kaps.resort.core.usecase.ad;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.HousingType;

import java.util.List;

public class GetCottagesForHomePageUseCase {

    private GetAdsByHousingType getAdsByHousingType;

    public GetCottagesForHomePageUseCase(GetAdsByHousingType getAdsByHousingType){
        this.getAdsByHousingType = getAdsByHousingType;
    }

    public List<Ad> getCottagesForHomePageUseCase(){
        List<Ad> ads = getAdsByHousingType.getAdsByHousingType(HousingType.COTTAGE);
        return ads;
    }

}
