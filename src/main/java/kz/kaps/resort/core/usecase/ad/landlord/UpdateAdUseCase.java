package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.ad.GetAd;
import kz.kaps.resort.core.usecase.ad.UpdateAd;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;

import java.util.Date;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.*;

public class UpdateAdUseCase {

    private GetUserByUsername getUserByUsername;
    private UpdateAd updateAd;
    private GetAd getAd;

    public UpdateAdUseCase(UpdateAd updateAd, GetAd getAd, GetUserByUsername getUserByUsername) {
        this.getUserByUsername = getUserByUsername;
        this.updateAd = updateAd;
        this.getAd = getAd;
    }

    public void updateAd(Ad ad, String ownerUsername) throws ForbiddenException {
        failsIfAdIsNull(ad);
        failsIfAdIdIsNull(ad.getId());
        failsIfOwnerUsernameIsEmpty(ownerUsername);

        Ad oldAd = getAd.getAd(ad.getId());
        failsIfAdIsNull(oldAd);

        User owner = getUserByUsername.getUserByUsername(ownerUsername);
        failsIfOwnerIsNull(owner);
        failIfUserDoesNotOwnAd(owner, oldAd);

        ad.setStatus(oldAd.getStatus());
        ad.setOwner(oldAd.getOwner());
        ad.setHousingType(oldAd.getHousingType());
        ad.setCreatedAt(oldAd.getCreatedAt());
        Date now = new Date();
        ad.setEditedAt(now);
        updateAd.updateAd(ad);
    }
}
