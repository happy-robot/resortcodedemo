package kz.kaps.resort.dataproviders.database.ad;

import kz.kaps.resort.core.domain.*;
import kz.kaps.resort.core.usecase.ad.*;
import kz.kaps.resort.core.usecase.ad.landlord.*;
import kz.kaps.resort.core.usecase.ad.landlord.getadforedit.GetAdForEditUseCase;
import kz.kaps.resort.core.usecase.ad.landlord.getlandlordads.AdShortProjection;
import kz.kaps.resort.core.usecase.ad.tenant.FacetedSearchByQuery;
import kz.kaps.resort.core.usecase.ad.tenant.ViewAdUseCase;
import kz.kaps.resort.dataproviders.database.dictionaries.locality.LocalityEntity;
import kz.kaps.resort.dataproviders.database.user.UserEntity;
import kz.kaps.resort.dataproviders.database.user.UserEntityConverter;
import kz.kaps.resort.core.usecase.ad.tenant.FacetedSearchFormDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AdDatabaseDataProvider implements DoesAdExist, GetAd, UpdateAd, GetAdsByHousingType,
        CreateAd, DeleteAdsByOwnerAndStatus,
        GetAdsByOwnerAndStatus,
        GetAdsByOwnerAndStatusAndHousingType, GetAdForEditUseCase.GetAdForEdit,
        UpdateAdStatus, DoesUserOwnAd, ViewAdUseCase.GetAd,
        FacetedSearchByQuery, GetAdsCountsByStatusAndUsername {

    private AdEntityRepository adEntityRepository;
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AdDatabaseDataProvider(AdEntityRepository adEntityRepository,
                                  ModelMapper modelMapper){
        this.adEntityRepository = adEntityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean doesAdExist(Long adId) {
        return adEntityRepository.existsById(adId);
    }


    @Override
    public Ad getAd(Long adId) {
        Optional<AdEntity> adEntity = adEntityRepository.findById(adId);
        if(adEntity.isPresent())
            return modelMapper.map(adEntity.get(), Ad.class);
        else return null;
    }

    @Override
    public void updateAd(Ad ad) {
        AdEntity entity = modelMapper.map(ad, AdEntity.class);
        adEntityRepository.save(entity);
    }

    @Override
    public List<Ad> getAdsByHousingType(HousingType housingType) {
        List<AdEntity> entities = adEntityRepository.findByHousingType(housingType);
        return entities.stream().map(e -> modelMapper.map(e, Ad.class)).collect(Collectors.toList());
    }

    @Override
    public Ad createAd(Ad ad) {
        AdEntity adEntity = modelMapper.map(ad, AdEntity.class);
        AdEntity createdAdEntity = adEntityRepository.save(adEntity);
        return modelMapper.map(createdAdEntity, Ad.class);
    }

    @Transactional
    @Override
    public void deleteAdsByOwnerAndStatus(User owner, AdStatusEnum status) {
        UserEntity ownerEntity = UserEntityConverter.convertUserToUserEntity(owner);
        adEntityRepository.deleteByOwnerAndStatus(ownerEntity, status);
    }

    @Override
    public List<AdShortProjection> getAdsByOwnerAndStatus(String ownerUsername, AdStatusEnum status) {
        return adEntityRepository.getByUsernameAndStatus(ownerUsername, status);
    }

    @Override
    public List<Ad> getAdsByOwnerAndStatusAndHousingType(Long ownerId, AdStatusEnum status, HousingType housingType) {
        return adEntityRepository.getByOwnerIdAndStatusAndHousingType(ownerId, status, housingType)
                .stream()
                .map(e -> modelMapper.map(e, Ad.class))
                .collect(Collectors.toList());
    }

    @Override
    public Ad getAdForEdit(Long adId) {
        EntityGraph<AdEntity> graph = entityManager.createEntityGraph(AdEntity.class);
        Subgraph<LocalityEntity> localitySubgraph = graph.addSubgraph(AdEntity.ATTRIBUTE_LOCALITY);
        localitySubgraph.addSubgraph(LocalityEntity.ATTRIBUTE_COUNTRY);
        AdEntity adEntity = entityManager.createNamedQuery(AdEntity.QUERY_DISTINCT_BY_ID, AdEntity.class)
                .setParameter(AdEntity.ATTRIBUTE_ID, adId)
                .setHint("javax.persistence.loadgraph", graph)
                .getSingleResult();
        return modelMapper.map(adEntity, Ad.class);
    }

    @Transactional
    @Override
    public void updateAdStatus(Long adId, AdStatusEnum status) {
        entityManager.createNamedQuery(AdEntity.QUERY_SET_STATUS)
        .setParameter(AdEntity.ATTRIBUTE_STATUS, status)
        .setParameter(AdEntity.ATTRIBUTE_ID, adId)
        .executeUpdate();
    }

    @Override
    public boolean doesUserOwnAd(String username, Long adId) {
        return entityManager.createNamedQuery(AdEntity.QUERY_DOES_USER_OWN_AD_HELPER, Long.class)
                .setParameter(UserEntity.ATTRIBUTE_USERNAME, username)
                .setParameter(AdEntity.ATTRIBUTE_ID, adId)
                .getSingleResult() > 0;
    }

    @Override
    public Ad getAdView(Long adId) {
        EntityGraph entityGraph = entityManager.getEntityGraph(AdEntity.GRAPH_LOCALITY_COUNTRY);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        AdEntity adEntity = entityManager.find(AdEntity.class, adId, properties);
        if(adEntity != null)
            return modelMapper.map(adEntity, Ad.class);
        else return null;
    }

    public Page<Ad> facetedSearchByQuery(FacetedSearchFormDto searchForm, Pageable pageable) {
        Session session = entityManager.unwrap( Session.class );
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<AdEntity> cr = cb.createQuery(AdEntity.class);

        Root<AdEntity> root = cr.from(AdEntity.class);
        Predicate predicate = getPredicate(root, cb, searchForm);
        cr.select(root).where(predicate);

        Sort sort = pageable.getSort();
        Sort.Order order = sort.getOrderFor(AdEntity.ATTRIBUTE_WEEKDAY_PRICE_PER_DAY);
        if(order != null) {
            Sort.Direction direction = order.getDirection();
            if (direction == Sort.Direction.ASC)
                cr.orderBy(cb.asc(root.get(AdEntity.ATTRIBUTE_WEEKDAY_PRICE_PER_DAY)));
            else if (direction == Sort.Direction.DESC)
                cr.orderBy(cb.desc(root.get(AdEntity.ATTRIBUTE_WEEKDAY_PRICE_PER_DAY)));
        }

        Query<AdEntity> query = session.createQuery(cr);
        List<AdEntity> results = query.setFirstResult(pageable.getPageSize() * pageable.getPageNumber()).setMaxResults(pageable.getPageSize()).getResultList();

        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<AdEntity> cRoot = countCq.from(cr.getResultType());
        Predicate countPredicate = getPredicate(cRoot, cb, searchForm);
        countCq.select(cb.count(cRoot));
        countCq.where(countPredicate);
        Long count = entityManager.createQuery(countCq).getSingleResult();

        return new PageImpl<>(
                results.stream().map(e -> modelMapper.map(e, Ad.class)).collect(Collectors.toList()),
                pageable, count
        );
    }


    private Predicate getPredicate(Root<AdEntity> root, CriteriaBuilder cb, FacetedSearchFormDto searchForm) {
        Predicate predicate = cb.conjunction();

        if(searchForm.getHousingType() != null && !searchForm.getHousingType().isEmpty()) {
            predicate = cb.and(predicate, root.get(AdEntity.ATTRIBUTE_HOUSING_TYPE).in(searchForm.getHousingType()));
        }
        if(searchForm.getLocalityId() != null && !searchForm.getLocalityId().isEmpty()) {
            predicate = cb.and(predicate, root.get(AdEntity.ATTRIBUTE_LOCALITY).in(searchForm.getLocalityId()));
        }
        if(searchForm.getStartRoomNum() != null) {
            predicate = cb.and(predicate, cb.ge(root.get(AdEntity.ATTRIBUTE_ROOM_NUM), searchForm.getStartRoomNum()));
        }
        if(searchForm.getEndRoomNum() != null) {
            predicate = cb.and(predicate, cb.le(root.get(AdEntity.ATTRIBUTE_ROOM_NUM), searchForm.getEndRoomNum()));
        }
        if(searchForm.getStartPrice() != null) {
            predicate = cb.and(predicate, cb.or(
                    cb.ge(root.get(AdEntity.ATTRIBUTE_WEEKDAY_PRICE_PER_DAY), searchForm.getStartPrice()),
                    cb.ge(root.get(AdEntity.ATTRIBUTE_HOLYDAY_PRICE_PER_DAY), searchForm.getStartPrice())
            ));
        }
        if(searchForm.getEndPrice() != null) {
            predicate = cb.and(predicate, cb.or(
                    cb.le(root.get(AdEntity.ATTRIBUTE_WEEKDAY_PRICE_PER_DAY), searchForm.getEndPrice()),
                    cb.le(root.get(AdEntity.ATTRIBUTE_HOLYDAY_PRICE_PER_DAY), searchForm.getEndPrice())
            ));
        }
        if(searchForm.getHotWater() != null && searchForm.getHotWater()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_HOT_WATER), searchForm.getHotWater()));
        }
        if(searchForm.getColdWater() != null && searchForm.getColdWater()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_COLD_WATER), searchForm.getColdWater()));
        }
        if(searchForm.getTv() != null && searchForm.getTv()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_TV), searchForm.getTv()));
        }
        if(searchForm.getFridge() != null && searchForm.getFridge()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_FRIDGE), searchForm.getFridge()));
        }
        if(searchForm.getStove() != null && searchForm.getStove()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_STOVE), searchForm.getStove()));
        }
        if(searchForm.getWasher() != null && searchForm.getWasher()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_WASHER), searchForm.getWasher()));
        }
        if(searchForm.getMicrowave() != null && searchForm.getMicrowave()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_MICROWAVE), searchForm.getMicrowave()));
        }
        if(searchForm.getWifi() != null && searchForm.getWifi()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_WIFI), searchForm.getWifi()));
        }
        if(searchForm.getConditioner() != null && searchForm.getConditioner()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_CONDITIONER), searchForm.getConditioner()));
        }
        if(searchForm.getBrazier() != null && searchForm.getBrazier()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_BRAZIER), searchForm.getBrazier()));
        }
        if(searchForm.getKazan() != null && searchForm.getKazan()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_KAZAN), searchForm.getKazan()));
        }
        if(searchForm.getSauna() != null && searchForm.getSauna()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_SAUNA), searchForm.getSauna()));
        }
        if(searchForm.getBathhouse() != null && searchForm.getBathhouse()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_BATHHOUSE), searchForm.getBathhouse()));
        }
        if(searchForm.getRestaurant() != null && searchForm.getRestaurant()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_RESTAURANT), searchForm.getRestaurant()));
        }
        if(searchForm.getSwimmingPool() != null && searchForm.getSwimmingPool()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_SWIMMING_POOL), searchForm.getSwimmingPool()));
        }
        if(searchForm.getBilliards() != null && searchForm.getBilliards()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_BILLIARDS), searchForm.getBilliards()));
        }
        if(searchForm.getSummerKitchen() != null && searchForm.getSummerKitchen()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_SUMMER_KITCHEN), searchForm.getSummerKitchen()));
        }
        if(searchForm.getCook() != null && searchForm.getCook()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_COOK), searchForm.getCook()));
        }
        if(searchForm.getAlcove() != null && searchForm.getAlcove()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_ALCOVE), searchForm.getAlcove()));
        }
        if(searchForm.getTapchan() != null && searchForm.getTapchan()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_TAPCHAN), searchForm.getTapchan()));
        }
        if(searchForm.getBicycles() != null && searchForm.getBicycles()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_BYCICLES), searchForm.getBicycles()));
        }
        if(searchForm.getQuadBikes() != null && searchForm.getQuadBikes()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_QUAD_BIKES), searchForm.getQuadBikes()));
        }
        if(searchForm.getPlayground() != null && searchForm.getPlayground()) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_PLAYGROUND), searchForm.getPlayground()));
        }
        if(searchForm.getStatus() != null) {
            predicate = cb.and(predicate, cb.equal(root.get(AdEntity.ATTRIBUTE_STATUS), searchForm.getStatus()));
        }

        return predicate;
    }

    @Override
    public List<AdsCountsByStatusProjection> getAdsCountsByStatusAndUsername(String ownerUsername) {
        return adEntityRepository.getAdsCountsByStatusAndUsername(ownerUsername);
    }
}
