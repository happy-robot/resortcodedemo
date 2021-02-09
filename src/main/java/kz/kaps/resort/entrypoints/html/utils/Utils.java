package kz.kaps.resort.entrypoints.html.utils;

import kz.kaps.resort.entrypoints.rest.landlord.*;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class Utils {

    private Utils(){}

    public static URI getUploadImageMethodURL(String adId){
        ControllerLinkBuilder uploadImageLinkBuilder = ControllerLinkBuilder.linkTo(methodOn(ImageUploadRestEndPoint.class)
                .upload(adId, null, null));
        return uploadImageLinkBuilder.toUri();
    }

    public static URI getDeleteImageMethodURL(String adId){
        ControllerLinkBuilder deleteImageLinkBuilder = ControllerLinkBuilder.linkTo(methodOn(ImageDeleteRestEndPoint.class)
                .delete(adId, null, null));
        return deleteImageLinkBuilder.toUri();
    }

    public static URI getImageListMethodURL(String adId){
        ControllerLinkBuilder listImageLinkBuilder = ControllerLinkBuilder.linkTo(methodOn(ImageListRestEndPoint.class)
                .getImages(adId, null));
        return listImageLinkBuilder.toUri();
    }


    public static String getFileExtension(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }
}
