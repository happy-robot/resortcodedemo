package kz.kaps.resort.entrypoints.rest.landlord;

import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.image.DeleteImageUseCase;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageDeleteRestEndPoint {
    private GetUserByUsername getUserByUsername;
    private DeleteImageUseCase deleteImageUseCase;

    public ImageDeleteRestEndPoint(
            GetUserByUsername getUserByUsername, DeleteImageUseCase deleteImageUseCase){
        this.getUserByUsername = getUserByUsername;
        this.deleteImageUseCase = deleteImageUseCase;
    }

    @DeleteMapping("/api/rest/v1/my/ads/{adId}/images/{id}")
    public ResponseEntity<String> delete(
            @PathVariable String adId, @PathVariable String id,
            Authentication authentication) {
        if(authentication == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        String username = authentication.getName();
        if(StringUtils.isEmpty(username)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if(StringUtils.isEmpty(adId)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = getUserByUsername.getUserByUsername(username);

        if(user == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Long adIdL = Long.valueOf(adId);
        Long idL = Long.valueOf(id);

        try {
            deleteImageUseCase.deleteAdImageUseCase(user, adIdL, idL);
        } catch (ForbiddenException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
