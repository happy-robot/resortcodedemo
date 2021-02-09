package kz.kaps.resort.core.usecase.ad;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.HousingType;

import java.util.List;

public class GetFlatsForHomePageUseCase {

    private GetAdsByHousingType getAdsByHousingType;

    public GetFlatsForHomePageUseCase(GetAdsByHousingType getAdsByHousingType){
        this.getAdsByHousingType = getAdsByHousingType;
    }

    public List<Ad> getFlatsForHomePageUseCase(){
        List<Ad> ads = getAdsByHousingType.getAdsByHousingType(HousingType.FLAT);
        return ads;
    }
}
