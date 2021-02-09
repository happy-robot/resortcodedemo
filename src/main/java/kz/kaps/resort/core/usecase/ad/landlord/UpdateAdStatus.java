package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.AdStatusEnum;

public interface UpdateAdStatus {

    void updateAdStatus(Long adId, AdStatusEnum status);

}
