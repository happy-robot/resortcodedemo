package kz.kaps.resort.core.usecase.image;

import kz.kaps.resort.core.domain.Image;

import java.util.Optional;

public interface GetImage {
    Optional<Image> getImage(Long id);
}
