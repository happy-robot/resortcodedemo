package kz.kaps.resort.core.usecase.ad;

import kz.kaps.resort.core.domain.Ad;

public class GetAdUseCase {

    private GetAd getAd;
    private DoesAdExist doesAdExist;
    private UpdateAd updateAd;

    public GetAdUseCase(GetAd getAd, DoesAdExist doesAdExist, UpdateAd updateAd){
        this.getAd = getAd;
        this.doesAdExist = doesAdExist;
        this.updateAd = updateAd;
    }

    public Ad getAd(Long adId){
        failIfAdDoesNotExist(adId);
        Ad ad = getAd.getAd(adId);
        recountViewsNum(ad);
        updateAd.updateAd(ad);
        return ad;
    }

    private void failIfAdDoesNotExist(Long adId){
        boolean adExists = doesAdExist.doesAdExist(adId);
        if(!adExists){
            throw new IllegalArgumentException();
        }
    }

    private void recountViewsNum(Ad ad){
        Integer oldViewsNum = ad.getViewsNum();
        Integer newViewsNum = oldViewsNum == null ? 1 : oldViewsNum + 1;
        ad.setViewsNum(newViewsNum);
    }

}
