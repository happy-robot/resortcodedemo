package kz.kaps.resort.dataproviders.database.dictionaries.country;

import kz.kaps.resort.dataproviders.database.dictionaries.country.CountryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<CountryEntity, Long> {
}
