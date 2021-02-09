package kz.kaps.resort.dataproviders.database.dictionaries.country;

import kz.kaps.resort.core.dictionaries.Country;
import kz.kaps.resort.core.dictionaries.GetAllCountries;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CountryDatabaseDataProvider implements GetAllCountries {

    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CountryDatabaseDataProvider(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        Iterable<CountryEntity> countryEntityIterable = countryRepository.findAll();
        countryEntityIterable.forEach(c -> countries.add(modelMapper.map(c, Country.class)));
        return countries;
    }
}
