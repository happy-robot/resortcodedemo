package kz.kaps.resort.dataproviders.database.pricetag;

import kz.kaps.resort.core.domain.PriceTag;
import kz.kaps.resort.dataproviders.database.ad.AdEntity;

public class PriceTagEntityConverter {

    public static PriceTagEntity toEntity(PriceTag priceTag, AdEntity adEntity){
        PriceTagEntity priceTagEntity = new PriceTagEntity.Builder()
                .id(priceTag.getId())
                .price(priceTag.getPrice())
                .startDate(priceTag.getStartDate())
                .endDate(priceTag.getEndDate())
                .ad(adEntity)
                .build();

        return priceTagEntity;
    }

    public static PriceTag fromEntity(PriceTagEntity entity){
        PriceTag priceTag = PriceTag.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
        return priceTag;
    }

}
