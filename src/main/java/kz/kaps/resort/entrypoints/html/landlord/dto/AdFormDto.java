package kz.kaps.resort.entrypoints.html.landlord.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.HousingType;
import kz.kaps.resort.entrypoints.html.landlord.validators.AdFormValid;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AdFormValid
public class AdFormDto {

    public static final String ATTRIBUTE_FLOOR = "floor";
    public static final String ATTRIBUTE_FLOOR_NUM = "floorNum";

    public static final String ATTRIBUTE_ID = "id";
    public static final String ATTRIBUTE_HEADER = "header";
    public static final String ATTRIBUTE_ROOM_NUM = "roomNum";
    public static final String ATTRIBUTE_BUILDING_FLOORS = "buildingFloors";
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
    public static final String ATTRIBUTE_COUNTRY_ID = "countryId";
    public static final String ATTRIBUTE_LOCALITY_ID = "localityId";
    public static final String ATTRIBUTE_STREET = "street";
    public static final String ATTRIBUTE_HOUSE_NUMBER = "houseNumber";
    public static final String ATTRIBUTE_WEEKDAY_PRICE_PER_DAY = "weekdayPricePerDay";
    public static final String ATTRIBUTE_HOLYDAY_PRICE_PER_DAY = "holidayPricePerDay";

    public interface FlatGroup { }
    public interface CottageGroup { }
    public interface HouseGroup { }
    public interface SummerhouseGroup { }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    private HousingType housingType;

    @NotNull(groups = SummerhouseGroup.class)
    private Boolean isPricePerPerson;

    @Min(value = 1, groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    private Integer weekdayPricePerDay;

    @Min(value = 1, groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    private Integer holidayPricePerDay;

    @Min(value = 1, groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class})
    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class})
    private Integer roomNum;

    @Min(value = 1, groups = {FlatGroup.class})
    @NotNull(groups = {FlatGroup.class})
    private Integer floor;

    @Min(value = 2, groups = FlatGroup.class)
    @Min(value = 1, groups = {CottageGroup.class, HouseGroup.class})
    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class})
    private Integer floorNum;

    @Min(value = 1, groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class})
    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class})
    private Integer sleepNum;

    @Min(value = 1, groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class})
    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class})
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

    @NotBlank(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    private String header;

    @Size(min = 1, max = 1000, groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    @NotBlank(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    private String description;

    @Min(value = 1, groups = {SummerhouseGroup.class})
    @NotNull(groups = {SummerhouseGroup.class})
    private Integer summerHousesCount;

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

    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    private Long localityId;

    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    private Long countryId;

    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    private String street;

    @NotNull(groups = {FlatGroup.class, CottageGroup.class, HouseGroup.class, SummerhouseGroup.class})
    private String houseNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AdStatusEnum status;
}
