package kz.kaps.resort.core.usecase.ad.landlord.getlandlordads;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.HousingType;
import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdShortProjection {
    private Long id;
    private String header;
    private HousingType housingType;
    private String imageFileNameWithExt;
    private Integer summerHousesCount;
    private Integer viewsNum;
    private AdStatusEnum status;
    private String localityNameRu;
    private String localityNameEn;
    private Integer floorNum;
    private Integer roomNum;
    private Integer area;
    private Integer weekdayPricePerDay;
    private Integer holidayPricePerDay;
}
