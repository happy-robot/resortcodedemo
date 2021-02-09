package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.usecase.ad.DoesAdExist;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.*;

public class ActivateAdUseCase {

    private DoesAdExist doesAdExist;
    private UpdateAdStatus updateAdStatus;
    private DoesUserOwnAd doesUserOwnAd;

    public ActivateAdUseCase(DoesAdExist doesAdExist, UpdateAdStatus updateAdStatus,
                             DoesUserOwnAd doesUserOwnAd) {
        this.doesAdExist = doesAdExist;
        this.updateAdStatus = updateAdStatus;
        this.doesUserOwnAd = doesUserOwnAd;
    }

    public void activateAd(Long adId, String username) throws ForbiddenException {
        failsIfOwnerUsernameIsEmpty(username);
        failsIfAdIdIsNull(adId);
        failsIfAdDoesNotExists(adId, doesAdExist);
        failsIfUserDoesNotOwnAd(username, adId, doesUserOwnAd);

        updateAdStatus.updateAdStatus(adId, AdStatusEnum.PUBLISHED);
    }
}
