package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.AdStatusEnum;
import lombok.Data;

@Data
public class AdsCountsByStatusProjection {
    private AdStatusEnum status;
    private long count;

    public AdsCountsByStatusProjection(AdStatusEnum status, long count) {
        this.status = status;
        this.count = count;
    }
}
