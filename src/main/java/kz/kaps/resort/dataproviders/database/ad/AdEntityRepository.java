package kz.kaps.resort.dataproviders.database.ad;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.HousingType;
import kz.kaps.resort.core.usecase.ad.landlord.AdsCountsByStatusProjection;
import kz.kaps.resort.core.usecase.ad.landlord.getlandlordads.AdShortProjection;
import kz.kaps.resort.dataproviders.database.user.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface AdEntityRepository extends CrudRepository<AdEntity, Long> {

    List<AdEntity> findByHousingType(HousingType housingType);

    List<AdEntity> getByOwner(UserEntity owner);

    void deleteByOwnerAndStatus(UserEntity owner, AdStatusEnum status);

    @Query(
            value = "SELECT " +
                    "new kz.kaps.resort.core.usecase.ad.landlord.getlandlordads.AdShortProjection(ad.id, ad.header, ad.housingType, img.fileNameWithExt, ad.summerHousesCount, " +
                    "ad.viewsNum, ad.status, locality.nameRu, locality.nameEn, ad.floorNum, ad.roomNum, ad.area, ad.weekdayPricePerDay, ad.holidayPricePerDay) " +
                    "FROM ImageEntity img JOIN img.ad ad JOIN ad.owner owner JOIN ad.locality locality " +
                    "WHERE owner.username = :username and ad.status = :status and img.orderNumber = 1 order by ad.editedAt desc")
    List<AdShortProjection> getByUsernameAndStatus(String username, AdStatusEnum status);

    List<AdEntity> getByOwnerIdAndStatusAndHousingType(Long ownerId, AdStatusEnum status, HousingType housingType);

    @Query(value = "SELECT new kz.kaps.resort.core.usecase.ad.landlord.AdsCountsByStatusProjection(a.status, COUNT(a)) " +
            "FROM AdEntity a WHERE a.owner.username = :username GROUP BY a.status")
    List<AdsCountsByStatusProjection> getAdsCountsByStatusAndUsername(String username);
}
