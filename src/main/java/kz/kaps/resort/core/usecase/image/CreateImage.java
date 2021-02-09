package kz.kaps.resort.core.usecase.image;

import kz.kaps.resort.core.domain.Image;

public interface CreateImage {

    Image createImage(Image image, Long adId);

}
