package kz.kaps.resort.core.usecase.image;

import kz.kaps.resort.core.constants.ImageSize;
import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.Image;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.properties.ImageProperties;
import kz.kaps.resort.core.usecase.ad.DoesAdExist;
import kz.kaps.resort.core.usecase.ad.GetAd;
import kz.kaps.resort.core.usecase.ad.landlord.DoesAdOwnImage;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.*;

public class DeleteImageUseCase {

    private DoesAdExist doesAdExist;
    private GetAd getAd;
    private DoesImageExist doesImageExist;
    private DoesAdOwnImage doesAdOwnImage;
    private GetImage getImage;
    private DeleteImage deleteImage;
    private ImageProperties imageProperties;

    public DeleteImageUseCase(
            DoesAdExist doesAdExist, GetAd getAd, DoesImageExist doesImageExist,
            DoesAdOwnImage doesAdOwnImage,
            GetImage getImage, DeleteImage deleteImage,
            ImageProperties imageProperties){
        this.doesAdExist = doesAdExist;
        this.getAd = getAd;
        this.doesImageExist = doesImageExist;
        this.doesAdOwnImage = doesAdOwnImage;
        this.getImage = getImage;
        this.deleteImage = deleteImage;
        this.imageProperties = imageProperties;
    }

    public void deleteAdImageUseCase(User owner, Long adId, Long imageId)
            throws ForbiddenException, IOException {
        failsIfOwnerIsNull(owner);
        failsIfAdIdIsNull(adId);
        failsIfImageIdIsNull(imageId);

        failIfAdDoesNotExist(adId);
        failIfUserDoesNotOwnAd(owner, adId);
        failIfImageDoesNotExist(imageId);
        failIfAdDoesNotOwnImage(imageId, adId);
        Optional<Image> image = getImage.getImage(imageId);
        if(image.isPresent()) {
            deleteImage.deleteImage(imageId);
            deleteImgFromFileSystem(image.get());
        }
    }

    private void failIfAdDoesNotExist(Long adId){
        boolean adExists = doesAdExist.doesAdExist(adId);
        if(!adExists){
            throw new IllegalArgumentException();
        }
    }

    private void failIfUserDoesNotOwnAd(User testUser, Long adId) throws ForbiddenException {
        Ad ad = getAd.getAd(adId);
        User owner = ad.getOwner();
        if(!owner.getId().equals(testUser.getId()))
            throw new ForbiddenException();
    }

    private void failIfImageDoesNotExist(Long id){
        if(!doesImageExist.doesImageExist(id))
            throw new IllegalArgumentException();
    }

    private void failIfAdDoesNotOwnImage(Long id, Long adId) throws ForbiddenException {
        if(!doesAdOwnImage.doesAdOwnImage(adId, id)) throw new ForbiddenException();
    }

    private void deleteImgFromFileSystem(Image image) throws IOException {
        for(ImageSize imageSize: ImageSize.values()){
            deleteImgFromFileSystem(image, imageSize);
        }
    }

    private void deleteImgFromFileSystem(Image image, ImageSize imageSize) throws IOException {
        String fileNameWithExt = image.getFileNameWithExt();
        String resizedImgsFolder = ImageUtils.getImageFolder(imageSize, imageProperties);
        Path filepath = Paths.get(resizedImgsFolder, fileNameWithExt);
        File file = new File(filepath.toUri());
        boolean success = file.delete();
        if(!success) throw new IOException();
    }
}
