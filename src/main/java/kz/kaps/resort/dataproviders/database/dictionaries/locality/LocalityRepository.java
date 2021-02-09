package kz.kaps.resort.dataproviders.database.dictionaries.locality;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.List;

public interface LocalityRepository extends CrudRepository<LocalityEntity, Long> {

    @Query(value = "SELECT DISTINCT l FROM LocalityEntity l JOIN l.ads a order by l.nameRu asc")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<LocalityEntity> getLocalitiesWhereAdsExist();

}
