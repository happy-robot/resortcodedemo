package kz.kaps.resort.entrypoints.html.landlord.dto;


import kz.kaps.resort.core.domain.*;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@Setter
public class AdCottageFormDto {

    public static final String ATTRIBUTE_ID = "id";
    public static final String ATTRIBUTE_HEADER = "header";
    public static final String ATTRIBUTE_STATUS = "status";
    public static final String ATTRIBUTE_ROOM_NUM = "roomNum";
    public static final String ATTRIBUTE_FLOOR_NUM = "floorsNum";
    public static final String ATTRIBUTE_SLEEP_NUM = "sleepNum";
    public static final String ATTRIBUTE_AREA = "area";

    public static final String ATTRIBUTE_DESCRIPTION = "description";
    public static final String ATTRIBUTE_HOT_WATER = "hotWater";
    public static final String ATTRIBUTE_COLD_WATER = "coldWater";
    public static final String ATTRIBUTE_TV = "tv";
    public static final String ATTRIBUTE_FRIDGE = "fridge";
    public static final String ATTRIBUTE_STOVE = "stove";
    public static final String ATTRIBUTE_WASHER = "washer";
    public static final String ATTRIBUTE_MICROWAVE = "microwave";
    public static final String ATTRIBUTE_WIFI = "wifi";
    public static final String ATTRIBUTE_CONDITIONER = "conditioner";
    public static final String ATTRIBUTE_BRAZIER = "brazier";

    public static final String ATTRIBUTE_SAUNA = "sauna";
    public static final String ATTRIBUTE_BATHHOUSE = "bathhouse";
    public static final String ATTRIBUTE_SWIMMING_POOL = "swimmingPool";
    public static final String ATTRIBUTE_SUMMER_KITCHEN = "summerKitchen";
    public static final String ATTRIBUTE_COOK = "cook";
    public static final String ATTRIBUTE_ALCOVE = "alcove";
    public static final String ATTRIBUTE_TERRITORY_AREA = "territoryArea";
    public static final String ATTRIBUTE_KAZAN = "kazan";
    public static final String ATTRIBUTE_TAPCHAN = "tapchan";
    public static final String ATTRIBUTE_BICYCLES = "bicycles";
    public static final String ATTRIBUTE_QUAD_BIKES = "quadBikes";
    public static final String ATTRIBUTE_PLAY_GROUND = "playground";
    public static final String ATTRIBUTE_BILLIARDS = "billiards";

    public static final String ATTRIBUTE_COUNTRY_ID = "countryId";
    public static final String ATTRIBUTE_LOCALITY_ID = "localityId";
    public static final String ATTRIBUTE_STREET = "street";
    public static final String ATTRIBUTE_HOUSE_NUMBER = "houseNumber";
    public static final String ATTRIBUTE_WEEKDAY_PRICE_PER_DAY = "weekdayPricePerDay";
    public static final String ATTRIBUTE_HOLYDAY_PRICE_PER_DAY = "holidayPricePerDay";


    @NotNull
    private Long id;
    @NotBlank
    private String header;

    private AdStatusEnum status;
    @Min(1)
    @NotNull
    private Integer roomNum;
    @Min(1)
    @NotNull
    private Integer floorsNum;
    @Min(1)
    @NotNull
    private Integer sleepNum;

    @Min(1)
    @NotNull
    private Integer area;
    @Size(min = 1, max = 1000)
    @NotBlank
    private String description;
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

    private Boolean sauna;
    private Boolean bathhouse;
    private Boolean swimmingPool;
    private Boolean summerKitchen;//летняя кухня
    private Boolean cook;//повар
    private Boolean alcove;//беседка
    @NotNull
    @Min(1)
    private Integer territoryArea;//площадь территории в сотках
    private Boolean kazan;//казан
    private Boolean tapchan;//тапчан
    private Boolean bicycles;//велосипеды
    private Boolean quadBikes;//квадроциклы
    private Boolean playground;//детская площадка
    private Boolean billiards;

    @NotNull
    private Long countryId;
    @NotNull
    private Long localityId;
    @NotBlank
    private String street;
    @NotBlank
    private String houseNumber;
    @Min(1)
    @NotNull
    private Integer weekdayPricePerDay;
    @Min(1)
    @NotNull
    private Integer holidayPricePerDay;

}
