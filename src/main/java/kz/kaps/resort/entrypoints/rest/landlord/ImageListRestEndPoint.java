package kz.kaps.resort.entrypoints.rest.landlord;

import kz.kaps.resort.core.usecase.image.GetImagesUseCase;
import kz.kaps.resort.entrypoints.html.ImageDto;
import kz.kaps.resort.entrypoints.html.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ImageListRestEndPoint {

    private GetImagesUseCase getImagesUseCase;
    private DtoConverter dtoConverter;

    @Autowired
    public ImageListRestEndPoint(GetImagesUseCase getImagesUseCase, DtoConverter dtoConverter) {
        this.getImagesUseCase = getImagesUseCase;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping("/api/rest/v1/my/ads/{adId}/images")
    public List<ImageDto> getImages(@PathVariable String adId, Authentication authentication) {
        Long adIdL = Long.valueOf(adId);
        return getImagesUseCase.listImages(adIdL).stream().map(dtoConverter::toDto).collect(Collectors.toList());
    }

}
