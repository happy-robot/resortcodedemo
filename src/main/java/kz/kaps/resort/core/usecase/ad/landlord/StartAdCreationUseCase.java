package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.*;

import java.util.Date;
import java.util.Optional;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.failsIfHousingTypeIsNull;
import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.failsIfOwnerIsNull;

public class StartAdCreationUseCase {

    private CreateAd createAd;
    private GetAdsByOwnerAndStatusAndHousingType getAdsByOwnerAndStatusAndHousingType;

    public StartAdCreationUseCase(CreateAd createAd,
                                  GetAdsByOwnerAndStatusAndHousingType getAdsByOwnerAndStatusAndHousingType){
        this.createAd = createAd;
        this.getAdsByOwnerAndStatusAndHousingType = getAdsByOwnerAndStatusAndHousingType;
    }

    public Ad startAdCreation(HousingType housingType, User user){
        failsIfOwnerIsNull(user);
        failsIfHousingTypeIsNull(housingType);

        Optional<Ad> oldSeed = getAlreadyExistSeed(housingType, user);
        if(oldSeed.isPresent()) {
            return oldSeed.get();
        } else {
            return createAdSeed(user, housingType);
        }
    }

    private Ad createAdSeed(User user, HousingType housingType){
        Date now = new Date();
        Ad ad = new Ad();
        ad.setOwner(user);
        ad.setHousingType(housingType);
        ad.setCreatedAt(now);
        ad.setEditedAt(now);
        ad.setStatus(AdStatusEnum.SEED);
        return createAd.createAd(ad);
    }

    private Optional<Ad> getAlreadyExistSeed(HousingType housingType, User owner){
        return getAdsByOwnerAndStatusAndHousingType.getAdsByOwnerAndStatusAndHousingType(owner.getId(), AdStatusEnum.SEED, housingType)
                .stream()
                .findFirst();
    }
}
