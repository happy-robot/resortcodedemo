package kz.kaps.resort.entrypoints.html.landlord.dto;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.HousingType;
import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdShortDto {

    private Long id;
    private String header;
    private HousingType housingType;
    private String imageUrlForMediumSize;
    private Integer summerHousesCount;
    private Integer viewsNum;
    private AdStatusEnum status;
    private String localityNameRu;
    private String localityNameEn;
    private Integer weekdayPricePerDay;
    private Integer holidayPricePerDay;
}
