package kz.kaps.resort.core.usecase.ad;

import kz.kaps.resort.core.dictionaries.GetAllLocalities;
import kz.kaps.resort.core.dictionaries.Locality;

import java.util.List;

public class GetFacetedSearchLocalitiesUseCase {

    private GetAllLocalities getLocalities;

    public GetFacetedSearchLocalitiesUseCase(GetAllLocalities getLocalities) {
        this.getLocalities = getLocalities;
    }

    public List<Locality> getFacetedSearchLocalities() {
        return getLocalities.getAllLocalities();
    }
}
