package kz.kaps.resort.dataproviders.database.pricetag;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Set;

public interface PriceTagRepository extends CrudRepository<PriceTagEntity, Long> {

    @Query("select p from PriceTagEntity as p where p.ad.id = ?1 and p.startDate < ?2 and p.endDate > ?3 or " +
            "(p.startDate between ?2 and ?3) or (p.endDate between ?2 and ?3)")
    Set<PriceTagEntity> findByDates(Long idId, LocalDate startDate, LocalDate endDate);

}
