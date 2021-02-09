package kz.kaps.resort.entrypoints.rest.dictionaries;

import kz.kaps.resort.core.dictionaries.Country;
import kz.kaps.resort.core.dictionaries.GetAllCountries;
import kz.kaps.resort.entrypoints.html.landlord.dto.CountryDto;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountriesRestEndPoint {

    private GetAllCountries getAllCountries;
    private ModelMapper modelMapper;

    public CountriesRestEndPoint(
            GetAllCountries getAllCountries, ModelMapper modelMapper) {
        this.getAllCountries = getAllCountries;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/rest/v1/dictionaries/countries")
    public List<CountryDto> getCountries() {
        List<Country> countries = getAllCountries.getAllCountries();
        List<CountryDto> countryDtoList = new ArrayList<>(countries.size());
        countries.forEach(c -> countryDtoList.add(modelMapper.map(c, CountryDto.class)));
        return countryDtoList;
    }

}
