package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.usecase.ad.landlord.getlandlordads.AdShortProjection;

import java.util.List;

public interface GetAdsByOwnerAndStatus {
    List<AdShortProjection> getAdsByOwnerAndStatus(String ownerUsername, AdStatusEnum status);
}
