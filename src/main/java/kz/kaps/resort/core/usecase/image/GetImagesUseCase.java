package kz.kaps.resort.core.usecase.image;

import kz.kaps.resort.core.domain.Image;

import java.util.List;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.failsIfAdIdIsNull;

public class GetImagesUseCase {

    private GetImagesByAdId getImagesByAdId;

    public GetImagesUseCase(GetImagesByAdId getImagesByAdId){
        this.getImagesByAdId = getImagesByAdId;
    }

    public List<Image> listImages(Long adId){
        failsIfAdIdIsNull(adId);
        return getImagesByAdId.getImagesByAdId(adId);
    }

}
