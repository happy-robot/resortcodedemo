package kz.kaps.resort.entrypoints.rest.landlord;

import kz.kaps.resort.core.domain.Image;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.image.UploadImageUseCase;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import kz.kaps.resort.entrypoints.html.ImageDto;
import kz.kaps.resort.entrypoints.html.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageUploadRestEndPoint {

    private UploadImageUseCase uploadImageUseCase;
    private GetUserByUsername getUserByUsername;
    private DtoConverter dtoConverter;

    @Autowired
    public ImageUploadRestEndPoint(UploadImageUseCase uploadImageUseCase,
                                   GetUserByUsername getUserByUsername, DtoConverter dtoConverter){
        this.uploadImageUseCase = uploadImageUseCase;
        this.getUserByUsername = getUserByUsername;
        this.dtoConverter = dtoConverter;
    }

    //В параметры передается массив MultipartFile[] потому что такие параметры передает библиотека на фронтенде HSFileAttachment
    @PostMapping("/api/rest/v1/my/ads/{adId}/images")
    public ResponseEntity<ImageDto> upload(@PathVariable String adId,
                                           @RequestParam("images[]") MultipartFile[] images,
                                           Authentication authentication) {
        if(authentication == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        String username = authentication.getName();
        if(StringUtils.isEmpty(username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if(StringUtils.isEmpty(adId)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = getUserByUsername.getUserByUsername(username);

        if(user == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if(images == null || images.length == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Long adIdL = Long.valueOf(adId);

        try {
            Image createdImage = uploadImageUseCase.uploadImage(user, adIdL, images[0]);
            return new ResponseEntity<>(dtoConverter.toDto(createdImage), HttpStatus.OK);
        } catch (ForbiddenException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
