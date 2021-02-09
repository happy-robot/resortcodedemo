package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.User;

public interface DeleteAdsByOwnerAndStatus {

    void deleteAdsByOwnerAndStatus(User owner, AdStatusEnum status);

}
