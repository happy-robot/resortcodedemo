package kz.kaps.resort.core.usecase.ad.landlord;

import java.util.List;

public interface GetAdsCountsByStatusAndUsername {
    List<AdsCountsByStatusProjection> getAdsCountsByStatusAndUsername(String ownerUsername);
}
