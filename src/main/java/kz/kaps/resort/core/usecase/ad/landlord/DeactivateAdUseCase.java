package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.usecase.ad.DoesAdExist;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.*;

public class DeactivateAdUseCase {

    private DoesAdExist doesAdExist;
    private DoesUserOwnAd doesUserOwnAd;
    private UpdateAdStatus updateAdStatus;

    public DeactivateAdUseCase(DoesAdExist doesAdExist, DoesUserOwnAd doesUserOwnAd, UpdateAdStatus updateAdStatus) {
        this.doesAdExist = doesAdExist;
        this.doesUserOwnAd = doesUserOwnAd;
        this.updateAdStatus = updateAdStatus;
    }

    public void deactivateAd(Long adId, String username) throws ForbiddenException {
        failsIfOwnerUsernameIsEmpty(username);
        failsIfAdIdIsNull(adId);
        failsIfAdDoesNotExists(adId, doesAdExist);
        failsIfUserDoesNotOwnAd(username, adId, doesUserOwnAd);

        updateAdStatus.updateAdStatus(adId, AdStatusEnum.DRAFT);
    }
}
