package kz.kaps.resort.core.usecase.ad.tenant;

import kz.kaps.resort.core.domain.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FacetedSearchByQuery {
    Page<Ad> facetedSearchByQuery(FacetedSearchFormDto searchForm, Pageable pageable);
}
