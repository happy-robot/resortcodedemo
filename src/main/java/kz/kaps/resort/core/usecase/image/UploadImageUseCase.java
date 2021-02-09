package kz.kaps.resort.core.usecase.image;

import kz.kaps.resort.core.constants.ImageSize;
import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.Image;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.properties.ImageProperties;
import kz.kaps.resort.core.usecase.ad.*;
import kz.kaps.resort.core.usecase.image.resizing.ResizeCommandDto;
import kz.kaps.resort.core.usecase.image.resizing.ResizeImagesService;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.entrypoints.html.utils.Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.*;

public class UploadImageUseCase {

    private DoesAdExist doesAdExist;
    private GetAd getAd;
    private CountImages countImages;
    private ImageProperties imageProperties;
    private CreateImage createImage;
    private ResizeImagesService resizeImagesService;

    public UploadImageUseCase(DoesAdExist doesAdExist, GetAd getAd, CountImages countImages,
                              ImageProperties imageProperties, CreateImage createImage,
                              ResizeImagesService resizeImagesService) {
        this.doesAdExist = doesAdExist;
        this.getAd = getAd;
        this.countImages = countImages;
        this.imageProperties = imageProperties;
        this.createImage = createImage;
        this.resizeImagesService = resizeImagesService;
    }

    public Image uploadImage(User owner, Long adId, MultipartFile imageMultipart)
            throws ForbiddenException, IOException {
        failsIfAdIdIsNull(adId);
        failsIfOwnerIsNull(owner);
        failsIfMultipartFileIsNull(imageMultipart);

        failsIfAdDoesNotExists(adId, doesAdExist);

        Ad ad = getAd.getAd(adId);
        failIfUserDoesNotOwnAd(owner, ad);

        String originalFileNameWithExt = imageMultipart.getOriginalFilename();
        String mimeType = imageMultipart.getContentType();
        int orderNumber = generateOrderNumber(adId);

        String generatedFileNameWithExt = UUID.randomUUID().toString() + '.' + Utils.getFileExtension(originalFileNameWithExt);

        Image image = Image.builder()
                .fileNameWithExt(generatedFileNameWithExt)
                .mimeType(mimeType)
                .orderNumber(orderNumber)
                .build();

        saveToFileSystem(imageMultipart, generatedFileNameWithExt);
        image = createImage.createImage(image, adId);
        resizeImagesService.resizeAndSaveImageFiles(getResizeCommandDto(image));
        return image;
    }

    private int generateOrderNumber(Long adId){
        return countImages.countImagesByAdId(adId) + 1;
    }
    private void saveToFileSystem(MultipartFile imageMultipart, String generatedFileNameWithExt) throws IOException {
        saveOriginalImg(imageMultipart, generatedFileNameWithExt);
    }
    private File saveOriginalImg(MultipartFile imageMultipart, String generatedFileNameWithExt) throws IOException {

        String originalImgsFolder = imageProperties.getFolderForOriginalImgs();
        Path filepath = Paths.get(originalImgsFolder, generatedFileNameWithExt);

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(imageMultipart.getBytes());
        }

        StringBuilder path = new StringBuilder();
        String imageFolder = getImageFolder(ImageSize.ORIGINAL);
        path.append(imageFolder);
        path.append(generatedFileNameWithExt);
        return new File(path.toString());
    }
    private String getImageFolder(ImageSize size){
        return ImageUtils.getImageFolder(size, imageProperties);
    }

    private ResizeCommandDto getResizeCommandDto(Image image) {
        ResizeCommandDto resizeCommandDto = new ResizeCommandDto();

        String originalImgsFolder = imageProperties.getFolderForOriginalImgs();
        Path originalImgPath = Paths.get(originalImgsFolder, image.getFileNameWithExt());
        resizeCommandDto.setOriginalImgPath(originalImgPath.toString());

        resizeCommandDto.addTargetImage(getTargetImage(image, ImageSize.EXTRA_SMALL));
        resizeCommandDto.addTargetImage(getTargetImage(image, ImageSize.SMALL));
        resizeCommandDto.addTargetImage(getTargetImage(image, ImageSize.MEDIUM));
        resizeCommandDto.addTargetImage(getTargetImage(image, ImageSize.BIG));
        resizeCommandDto.addTargetImage(getTargetImage(image, ImageSize.FULL));

        return resizeCommandDto;
    }
    private ResizeCommandDto.TargetImage getTargetImage(Image image, ImageSize size) {
        ResizeCommandDto.TargetImage targetImage = new ResizeCommandDto.TargetImage();

        String imageFolder = getImageFolder(size);
        Path targetImgPath = Paths.get(imageFolder, image.getFileNameWithExt());
        targetImage.setTargetImagePath(targetImgPath.toString());

        targetImage.setHeightInPixel(size.getHeight());
        targetImage.setWidthInPixel(size.getWidth());

        return targetImage;
    }
}
