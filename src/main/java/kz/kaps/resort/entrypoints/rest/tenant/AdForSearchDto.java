package kz.kaps.resort.entrypoints.rest.tenant;

import kz.kaps.resort.core.domain.HousingType;
import lombok.Data;

@Data
public class AdForSearchDto {
    private String urlLink;

    private String header;
    private String prices;
    private String imageUrlForMediumSize;
    private String localityName;

    private Long id;
    private Boolean isPricePerPerson;
    private Integer weekdayPricePerDay;
    private Integer holidayPricePerDay;
    private HousingType housingType;
    private Integer roomNum;
    private Integer floor;
    private Integer floorNum;
    private Integer sleepNum;
    private Integer area;
    private Boolean hotWater;
    private Boolean coldWater;
    private Boolean tv;
    private Boolean fridge;
    private Boolean stove;
    private Boolean washer;
    private Boolean microwave;
    private Boolean wifi;
    private Boolean conditioner;
    private Boolean brazier;
    private Boolean kazan;//казан
    private Boolean sauna;
    private Boolean bathhouse;
    private Boolean restaurant;
    private Boolean swimmingPool;
    private Boolean billiards;
    private Boolean summerKitchen;//летняя кухня
    private Boolean cook;//повар
    private Boolean alcove;//беседка
    private Integer territoryArea;//площадь территории в сотках
    private Boolean tapchan;//тапчан
    private Boolean bicycles;//велосипеды
    private Boolean quadBikes;//квадроциклы
    private Boolean playground;//детская площадка
}
