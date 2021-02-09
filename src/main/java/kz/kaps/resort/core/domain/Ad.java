package kz.kaps.resort.core.domain;

import kz.kaps.resort.core.dictionaries.Locality;
import lombok.*;

import java.util.*;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ad {

    private Long id;

    private User owner;

    private List<Image> images = new ArrayList<>();
    private Boolean isPricePerPerson;
    private Integer weekdayPricePerDay;
    private Integer holidayPricePerDay;
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


    private String header;

    private String description;

    private String shortDescription;

    private Date createdAt;

    private Date editedAt;

    private HousingType housingType;

    private Integer summerHousesCount;

    private Integer viewsNum;

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

    private Double latitude;

    private Double longitude;

    private AdStatusEnum status;

    private Locality locality;
    private String street;
    private String houseNumber;

}
