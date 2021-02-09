package kz.kaps.resort.dataproviders.database.images;

import kz.kaps.resort.core.domain.Image;
import kz.kaps.resort.core.usecase.ad.landlord.DoesAdOwnImage;
import kz.kaps.resort.core.usecase.image.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ImageDatabaseDataProvider implements CountImages, CreateImage,
        DoesAdOwnImage, DoesImageExist, GetImage, DeleteImage,
        DoesImageExistByAdId, GetImagesByAdId {

    private ImageEntityRepository imageEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ImageDatabaseDataProvider(ImageEntityRepository imageEntityRepository){
        this.imageEntityRepository = imageEntityRepository;
    }

    @Override
    public int countImagesByAdId(Long adId) {
        return imageEntityRepository.countByAdId(adId);
    }

    @Override
    public boolean doesImageExistById(Long adId) {
        return imageEntityRepository.existsByAdId(adId);
    }

    @Transactional
    @Override
    public Image createImage(Image image, Long adId) {
        BigInteger generatedId = (BigInteger) entityManager.createNativeQuery("SELECT nextval('IMAGES_SEQUENCE_ID')").getSingleResult();
        entityManager.createNativeQuery(
                "insert into rsrt_images (id_, ad_id_, file_name_with_ext_, mime_type_, order_number_) values (?,?,?,?,?)")
                .setParameter(1, generatedId.longValue())
                .setParameter(2, adId)
                .setParameter(3, image.getFileNameWithExt())
                .setParameter(4, image.getMimeType())
                .setParameter(5, image.getOrderNumber())
                .executeUpdate();
        image.setId(generatedId.longValue());
        return image;
    }

    @Override
    public boolean doesAdOwnImage(Long adId, Long imageId) {
        return imageEntityRepository.existsByIdAndAdId(imageId, adId);
    }

    @Override
    public boolean doesImageExist(Long id) {
        return imageEntityRepository.existsById(id);
    }

    @Override
    public void deleteImage(Long id) {
        imageEntityRepository.deleteById(id);
    }

    @Override
    public Optional<Image> getImage(Long id) {
        Optional<ImageEntity> imageEntity = imageEntityRepository.findById(id);
        if(imageEntity.isPresent()){
            Image image = ImageEntityConverter.fromEntity(imageEntity.get());
            return Optional.of(image);
        } else return Optional.empty();
    }

    @Override
    public List<Image> getImagesByAdId(Long adId) {
        List<ImageEntity> entities = imageEntityRepository.getAllByAdId(adId);
        return entities.stream().map(e -> ImageEntityConverter.fromEntity(e)).collect(Collectors.toList());
    }
}
