package kz.kaps.resort.core.usecase.image;

import kz.kaps.resort.core.constants.ImageSize;
import kz.kaps.resort.core.domain.Image;
import kz.kaps.resort.core.properties.ImageProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ImageUtils {

    public static String getImageFolder(ImageSize size, ImageProperties imageProperties){
        String folderPath = null;
        switch (size){
            case EXTRA_SMALL:
                folderPath = imageProperties.getFolderForExtraSmallImgs();
                break;
            case SMALL:
                folderPath = imageProperties.getFolderForSmallImgs();
                break;
            case MEDIUM:
                folderPath = imageProperties.getFolderForMediumImgs();
                break;
            case BIG:
                folderPath = imageProperties.getFolderForBigImgs();
                break;
            case FULL:
                folderPath = imageProperties.getFolderForFullImgs();
                break;
            case ORIGINAL:
                folderPath = imageProperties.getFolderForOriginalImgs();
                break;
            default:;
                break;
        }

        return folderPath;
    }

    public static void deleteResizedImages(Image image, ImageProperties imageProperties) {
        for (ImageSize nxtImgSize: ImageSize.values()) {
            String imageFolder = ImageUtils.getImageFolder(nxtImgSize, imageProperties);
            Path targetImgPath = Paths.get(imageFolder, image.getFileNameWithExt());
            try {
                Files.deleteIfExists(targetImgPath);
            } catch (IOException e) {
                log.error("Could not delete image file {}", targetImgPath.toString(), e);
            }
        }
    }
}
