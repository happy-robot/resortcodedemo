package kz.kaps.resort.core.usecase.pricetag;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.PriceTag;

public interface CreatePriceTag {

    PriceTag createPriceTag(PriceTag priceTag, Ad ad);

}
