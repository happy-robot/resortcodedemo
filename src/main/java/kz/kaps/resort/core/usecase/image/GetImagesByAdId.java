package kz.kaps.resort.core.usecase.image;

import kz.kaps.resort.core.domain.Image;

import java.util.List;

public interface GetImagesByAdId {

    List<Image> getImagesByAdId(Long adId);

}
