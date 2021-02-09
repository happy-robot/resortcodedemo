package kz.kaps.resort.core.usecase.pricetag;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.PriceTag;

public interface UpdatePriceTag {

    void updatePriceTag(PriceTag priceTag, Ad ad);

}
