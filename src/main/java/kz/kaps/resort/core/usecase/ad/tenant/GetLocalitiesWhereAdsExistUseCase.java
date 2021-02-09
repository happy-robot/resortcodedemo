package kz.kaps.resort.core.usecase.ad.tenant;

import kz.kaps.resort.configuration.ResourceHandlerConfig;
import kz.kaps.resort.core.dictionaries.Locality;

import java.util.List;

public class GetLocalitiesWhereAdsExistUseCase {

    private GetLocalitiesWhereAdsExist getLocalitiesWhereAdsExist;

    public GetLocalitiesWhereAdsExistUseCase(GetLocalitiesWhereAdsExist getLocalitiesWhereAdsExist) {
        this.getLocalitiesWhereAdsExist = getLocalitiesWhereAdsExist;
    }

    public List<Locality> getLocalitiesWhereAdsExist() {
        List<Locality> localities = getLocalitiesWhereAdsExist.getLocalitiesWhereAdsExist();
        if(localities != null)
            localities.forEach(l -> {
                if(l.getImageFileNameWithExt() != null)
                    l.setImageUrl(ResourceHandlerConfig.LOCALITIES_URL + l.getImageFileNameWithExt());
            });
        return localities;
    }

    public interface GetLocalitiesWhereAdsExist {
        List<Locality> getLocalitiesWhereAdsExist();
    }
}
