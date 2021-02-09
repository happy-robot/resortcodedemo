package kz.kaps.resort.entrypoints.html;

import kz.kaps.resort.core.domain.HousingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleFaceteFilterDto {

    private String arriveDate;
    private String departDate;
    private HousingType housingType;

}
