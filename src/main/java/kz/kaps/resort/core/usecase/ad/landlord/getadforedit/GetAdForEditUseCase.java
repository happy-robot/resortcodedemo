package kz.kaps.resort.core.usecase.ad.landlord.getadforedit;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.ad.landlord.DoesUserOwnAd;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.*;

public class GetAdForEditUseCase {

    private GetAdForEdit getAd;
    private GetUserByUsername getUserByUsername;
    private DoesUserOwnAd doesUserOwnAd;

    public GetAdForEditUseCase(GetAdForEdit getAd, GetUserByUsername getUserByUsername,
                               DoesUserOwnAd doesUserOwnAd) {
        this.getAd = getAd;
        this.getUserByUsername = getUserByUsername;
        this.doesUserOwnAd = doesUserOwnAd;
    }

    public Ad getAdForEdit(Long adId, String ownerUsername) throws ForbiddenException {
        failsIfAdIdIsNull(adId);
        failsIfOwnerUsernameIsEmpty(ownerUsername);

        Ad ad = getAd.getAdForEdit(adId);
        failsIfAdIsNull(ad);

        User owner = getUserByUsername.getUserByUsername(ownerUsername);
        failsIfOwnerIsNull(owner);
        failsIfUserDoesNotOwnAd(owner.getUsername(), adId, doesUserOwnAd);

        return ad;
    }

    public interface GetAdForEdit {
        Ad getAdForEdit(Long adId);
    }
}
