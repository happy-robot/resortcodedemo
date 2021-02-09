package kz.kaps.resort.dataproviders.database.images;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageEntityRepository extends CrudRepository<ImageEntity, Long> {
    int countByAdId(Long adId);
    boolean existsByAdId(Long adId);
    boolean existsByIdAndAdId(Long imageId, Long adId);
    List<ImageEntity> getAllByAdId(Long adId);
}
