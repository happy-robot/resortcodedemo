package kz.kaps.resort.dataproviders.database.dictionaries.locality;

import kz.kaps.resort.core.dictionaries.GetAllLocalities;
import kz.kaps.resort.core.dictionaries.Locality;
import kz.kaps.resort.core.usecase.ad.tenant.GetLocalitiesWhereAdsExistUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LocalityDatabaseDataProvider implements GetAllLocalities,
        GetLocalitiesWhereAdsExistUseCase.GetLocalitiesWhereAdsExist {

    private LocalityRepository localityRepository;
    private ModelMapper modelMapper;

    @Autowired
    public LocalityDatabaseDataProvider(LocalityRepository localityRepository, ModelMapper modelMapper) {
        this.localityRepository = localityRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<Locality> getAllLocalities() {
        List<Locality> localities = new ArrayList<>();
        Iterable<LocalityEntity> localityEntityIterable = localityRepository.findAll();
        localityEntityIterable.forEach(c -> localities.add(modelMapper.map(c, Locality.class)));
        return localities;
    }

    @Override
    public List<Locality> getLocalitiesWhereAdsExist() {
        return localityRepository.getLocalitiesWhereAdsExist()
                .stream()
                .map(l -> modelMapper.map(l, Locality.class))
                .collect(Collectors.toList());
    }
}
