package kz.kaps.resort.core.usecase.ad.landlord;

public interface DoesAdOwnImage {
    boolean doesAdOwnImage(Long adId, Long imageId);
}
