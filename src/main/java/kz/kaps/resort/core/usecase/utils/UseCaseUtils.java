package kz.kaps.resort.core.usecase.utils;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.HousingType;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.ad.DoesAdExist;
import kz.kaps.resort.core.usecase.ad.landlord.DoesUserOwnAd;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


public class UseCaseUtils {

    private UseCaseUtils(){}

    public static void failsIfOwnerIsNull(User owner){
        if(owner == null) throw new IllegalArgumentException();
    }

    public static void failsIfAdIdIsNull(Long adId){
        if(adId == null) throw new IllegalArgumentException();
    }

    public static void failsIfAdIdIsNull(Ad ad){
        if(ad != null && ad.getId() == null) throw new IllegalArgumentException();
    }

    public static void failsIfAdDoesNotExists(Long id, DoesAdExist doesAdExist){
        if(!doesAdExist.doesAdExist(id)) throw new IllegalArgumentException();
    }

    public static void failsIfUserDoesNotOwnAd(String username, Long adId, DoesUserOwnAd doesUserOwnAd) throws ForbiddenException {
        if(!doesUserOwnAd.doesUserOwnAd(username, adId))
            throw new ForbiddenException();
    }

    public static void failIfUserDoesNotOwnAd(User user, Ad ad) throws ForbiddenException {
        if(!ad.getOwner().getId().equals(user.getId())) throw new ForbiddenException();
    }

    public static void failsIfOwnerUsernameIsEmpty(String ownerUsername) throws ForbiddenException {
        if(StringUtils.isEmpty(ownerUsername)) throw new ForbiddenException();
    }

    public static void failsIfAdIsNull(Ad ad){
        if(ad == null) throw new IllegalArgumentException();
    }

    public static void failsIfHousingTypeIsNull(HousingType housingType){
        if(housingType == null) throw new IllegalArgumentException();
    }

    public static void failsIfMultipartFileIsNull(MultipartFile file) {
        if(file == null) throw new IllegalArgumentException();
    }

    public static void failsIfImageIdIsNull(Long imageId) {
        if(imageId == null) throw new IllegalArgumentException();
    }
}
