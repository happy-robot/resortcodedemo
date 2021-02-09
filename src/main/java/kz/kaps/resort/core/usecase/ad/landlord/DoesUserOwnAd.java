package kz.kaps.resort.core.usecase.ad.landlord;

public interface DoesUserOwnAd {

    boolean doesUserOwnAd(String username, Long adId);

}
