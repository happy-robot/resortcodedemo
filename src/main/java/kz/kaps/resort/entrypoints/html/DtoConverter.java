package kz.kaps.resort.entrypoints.html;

import kz.kaps.resort.configuration.ResourceHandlerConfig;
import kz.kaps.resort.core.domain.Image;
import org.springframework.stereotype.Service;


@Service
public class DtoConverter {

    public ImageDto toDto(Image im) {
        return ImageDto.builder()
                .id(im.getId())
                .fileNameWithExt(im.getFileNameWithExt())
                .mimeType(im.getMimeType())
                .orderNumber(im.getOrderNumber())
                .urlForExtraSmallSize(ResourceHandlerConfig.EXTRA_SMALL_IMAGES_URL + im.getFileNameWithExt())
                .urlForSmallSize(ResourceHandlerConfig.SMALL_IMAGES_URL + im.getFileNameWithExt())
                .urlForMediumSize(ResourceHandlerConfig.MEDIUM_IMAGES_URL + im.getFileNameWithExt())
                .urlForBigSize(ResourceHandlerConfig.BIG_IMAGES_URL + im.getFileNameWithExt())
                .urlForFullSize(ResourceHandlerConfig.FULL_IMAGES_URL + im.getFileNameWithExt())
                .build();
    }

}
