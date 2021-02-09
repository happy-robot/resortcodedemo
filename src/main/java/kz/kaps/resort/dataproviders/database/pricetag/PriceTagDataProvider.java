package kz.kaps.resort.dataproviders.database.pricetag;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.PriceTag;
import kz.kaps.resort.core.usecase.pricetag.CreatePriceTag;
import kz.kaps.resort.core.usecase.pricetag.DeletePriceTag;
import kz.kaps.resort.core.usecase.pricetag.GetPriceTagsByAdBetweenDates;
import kz.kaps.resort.core.usecase.pricetag.UpdatePriceTag;
import kz.kaps.resort.dataproviders.database.ad.AdEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PriceTagDataProvider implements GetPriceTagsByAdBetweenDates, UpdatePriceTag, DeletePriceTag, CreatePriceTag {

    private PriceTagRepository priceTagRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PriceTagDataProvider(PriceTagRepository priceTagRepository, ModelMapper modelMapper){
        this.priceTagRepository = priceTagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<PriceTag> getPriceTagsBetweenDates(Long adId, LocalDate startDate, LocalDate endDate) {
        Set<PriceTagEntity> priceTagEntities = priceTagRepository.findByDates(adId, startDate, endDate);
        Set<PriceTag> priceTags = priceTagEntities.stream().map(e -> PriceTagEntityConverter.fromEntity(e))
                .collect(Collectors.toSet());
        if(priceTags == null) priceTags = Collections.emptySet();
        return priceTags;
    }

    @Override
    public void updatePriceTag(PriceTag priceTag, Ad ad) {
        AdEntity adEntity = modelMapper.map(ad, AdEntity.class);
        PriceTagEntity entity = PriceTagEntityConverter.toEntity(priceTag, adEntity);
        priceTagRepository.save(entity);
    }

    @Override
    public void deletePriceTag(Long id) {
        priceTagRepository.deleteById(id);
    }

    @Override
    public PriceTag createPriceTag(PriceTag priceTag, Ad ad) {
        AdEntity adEntity = modelMapper.map(ad, AdEntity.class);
        PriceTagEntity priceTagEntity = PriceTagEntityConverter.toEntity(priceTag, adEntity);
        PriceTagEntity createdPriceTagEntity = priceTagRepository.save(priceTagEntity);
        PriceTag createdPriceTag = PriceTagEntityConverter.fromEntity(createdPriceTagEntity);
        return createdPriceTag;
    }
}
