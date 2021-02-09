package kz.kaps.resort.dataproviders.database.images;

import kz.kaps.resort.core.domain.Image;

public class ImageEntityConverter {

    private ImageEntityConverter(){}

    public static Image fromEntity(ImageEntity imageEntity){
        return Image.builder()
                .id(imageEntity.getId())
                .fileNameWithExt(imageEntity.getFileNameWithExt())
                .mimeType(imageEntity.getMimeType())
                .orderNumber(imageEntity.getOrderNumber())
                .build();
    }

}
