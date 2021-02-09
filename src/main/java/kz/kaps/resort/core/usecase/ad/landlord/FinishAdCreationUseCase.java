package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.ad.GetAd;
import kz.kaps.resort.core.usecase.ad.UpdateAd;
import kz.kaps.resort.core.usecase.image.DoesImageExistByAdId;
import kz.kaps.resort.core.usecase.exception.AdAlreadyExistsException;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.core.usecase.exception.ImagesDoNotExistException;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import org.modelmapper.ModelMapper;

import java.util.Date;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.*;

public class FinishAdCreationUseCase {

    private GetUserByUsername getUserByUsername;
    private UpdateAd updateAd;
    private GetAd getAd;
    private DoesImageExistByAdId doesImageExistByAdId;
    private DoesUserOwnAd doesUserOwnAd;

    public FinishAdCreationUseCase(GetUserByUsername getUserByUsername,
                                   UpdateAd updateAd, GetAd getAd,
                                   DoesImageExistByAdId doesImageExistByAdId,
                                   DoesUserOwnAd doesUserOwnAd) {
        this.getUserByUsername = getUserByUsername;
        this.updateAd = updateAd;
        this.getAd = getAd;
        this.doesImageExistByAdId = doesImageExistByAdId;
        this.doesUserOwnAd = doesUserOwnAd;
    }

    public void finishAdCreationUseCase(Ad ad, String ownerUsername)
            throws ForbiddenException, ImagesDoNotExistException, AdAlreadyExistsException {

        failsIfAdIsNull(ad);
        failsIfAdIdIsNull(ad.getId());
        failsIfHousingTypeIsNull(ad.getHousingType());
        failsIfOwnerUsernameIsEmpty(ownerUsername);

        User owner = getUserByUsername.getUserByUsername(ownerUsername);
        failsIfOwnerIsNull(owner);

        Ad oldAd = getAd.getAd(ad.getId());
        failsIfAdIsNotSeed(oldAd);
        failsIfHousingTypeChanged(ad, oldAd);
        failsIfUserDoesNotOwnAd(owner.getUsername(), ad.getId(), doesUserOwnAd);

        if(!doesImageExistByAdId.doesImageExistById(ad.getId())) {
            throw new ImagesDoNotExistException();
        }

        ad.setStatus(AdStatusEnum.DRAFT);
        ad.setOwner(oldAd.getOwner());
        ad.setHousingType(oldAd.getHousingType());
        ad.setCreatedAt(oldAd.getCreatedAt());
        Date now = new Date();
        ad.setEditedAt(now);
        updateAd.updateAd(ad);
    }

    private void failsIfAdIsNotSeed(Ad ad) throws AdAlreadyExistsException {
        if(AdStatusEnum.SEED != ad.getStatus()) throw new AdAlreadyExistsException();
    }

    private void failsIfHousingTypeChanged(Ad newAd, Ad oldAd) {
        if(newAd.getHousingType() != oldAd.getHousingType()) throw new IllegalArgumentException();
    }
}
