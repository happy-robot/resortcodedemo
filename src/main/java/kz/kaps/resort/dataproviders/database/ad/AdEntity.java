package kz.kaps.resort.dataproviders.database.ad;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.HousingType;
import kz.kaps.resort.dataproviders.database.dictionaries.locality.LocalityEntity;
import kz.kaps.resort.dataproviders.database.images.ImageEntity;
import kz.kaps.resort.dataproviders.database.user.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NamedEntityGraph(name = AdEntity.GRAPH_LOCALITY_COUNTRY,
        attributeNodes = @NamedAttributeNode(value = AdEntity.ATTRIBUTE_LOCALITY, subgraph = "countryGraph"),
        subgraphs = @NamedSubgraph(name = "countryGraph", attributeNodes = @NamedAttributeNode(LocalityEntity.ATTRIBUTE_COUNTRY))
)
@NamedEntityGraph(name = AdEntity.GRAPH_IMAGES,
        attributeNodes = @NamedAttributeNode(value = AdEntity.ATTRIBUTE_IMAGES)
)
@NamedQuery(name = AdEntity.QUERY_DISTINCT_BY_ID, query = "SELECT DISTINCT a FROM AdEntity a where a.id = :" + AdEntity.ATTRIBUTE_ID)
@NamedQuery(name = AdEntity.QUERY_SET_STATUS, query = "UPDATE AdEntity a SET a.status = :" + AdEntity.ATTRIBUTE_STATUS + " where a.id = :" + AdEntity.ATTRIBUTE_ID)
@NamedQuery(name = AdEntity.QUERY_DOES_USER_OWN_AD_HELPER, query = "SELECT COUNT(*) FROM AdEntity a JOIN a.owner o WHERE o.username = :" + UserEntity.ATTRIBUTE_USERNAME +
    " and a.id = :" + AdEntity.ATTRIBUTE_ID)
@Table(name = "rsrt_ads")
public class AdEntity {

    public static final String GRAPH_LOCALITY_COUNTRY = "graph.AdLocalityCountry";
    public static final String QUERY_DISTINCT_BY_ID = "query.SelectDistinctById";
    public static final String QUERY_SET_STATUS = "query.SetStatus";
    public static final String QUERY_DOES_USER_OWN_AD_HELPER = "query.DoesuserOwnAdHelper";
    public static final String ATTRIBUTE_ID = "id";
    public static final String ATTRIBUTE_STATUS = "status";
    public static final String ATTRIBUTE_LOCALITY = "locality";
    public static final String ATTRIBUTE_SAUNA = "sauna";
    public static final String ATTRIBUTE_BATHHOUSE = "bathhouse";
    public static final String ATTRIBUTE_RESTAURANT = "restaurant";
    public static final String ATTRIBUTE_SWIMMING_POOL = "swimmingPool";
    public static final String ATTRIBUTE_BILLIARDS = "billiards";
    public static final String ATTRIBUTE_SUMMER_KITCHEN = "summerKitchen";
    public static final String ATTRIBUTE_COOK = "cook";
    public static final String ATTRIBUTE_ALCOVE = "alcove";
    public static final String ATTRIBUTE_TAPCHAN = "tapchan";
    public static final String ATTRIBUTE_BYCICLES = "bicycles";
    public static final String ATTRIBUTE_QUAD_BIKES = "quadBikes";
    public static final String ATTRIBUTE_PLAYGROUND = "playground";

    public static final String GRAPH_IMAGES = "graph.AdWithImages";
    public static final String ATTRIBUTE_HOUSING_TYPE = "housingType";
    public static final String ATTRIBUTE_IMAGES = "images";
    public static final String ATTRIBUTE_HOT_WATER = "hotWater";
    public static final String ATTRIBUTE_COLD_WATER = "coldWater";
    public static final String ATTRIBUTE_CONDITIONER = "conditioner";
    public static final String ATTRIBUTE_ROOM_NUM= "roomNum";
    public static final String ATTRIBUTE_WEEKDAY_PRICE_PER_DAY= "weekdayPricePerDay";
    public static final String ATTRIBUTE_HOLYDAY_PRICE_PER_DAY= "holidayPricePerDay";
    public static final String ATTRIBUTE_TV = "tv";
    public static final String ATTRIBUTE_FRIDGE = "fridge";
    public static final String ATTRIBUTE_STOVE = "stove";
    public static final String ATTRIBUTE_WASHER = "washer";
    public static final String ATTRIBUTE_MICROWAVE = "microwave";
    public static final String ATTRIBUTE_WIFI = "wifi";
    public static final String ATTRIBUTE_BRAZIER = "brazier";
    public static final String ATTRIBUTE_KAZAN = "kazan";

    @Id
    @Column(name = "id_")
    @GeneratedValue
    private Long id;

    @Column(name = "weekday_price_per_day_", nullable = false)
    private Integer weekdayPricePerDay;

    @Column(name = "holiday_price_per_day_")
    private Integer holidayPricePerDay;

    @OneToMany(mappedBy = ImageEntity.ATTRIBUTE_AD)
    private List<ImageEntity> images = new ArrayList<>();
    /**
     * Price per day per person, otherwise per day
     */
    @Column(name = "is_price_per_person_", nullable = false, columnDefinition = "boolean default false")
    private Boolean isPricePerPerson = false;

    @Column(name = "room_num_")
    private Integer roomNum;

    @Column(name = "floor_")
    private Integer floor;

    @Column(name = "floor_num_")
    private Integer floorNum;

    @Column(name = "sleep_num_")
    private Integer sleepNum;

    @Column(name = "area_")
    private Integer area;

    @Column(name = "hot_water_")
    private Boolean hotWater;

    @Column(name = "cold_water_")
    private Boolean coldWater;

    @Column(name = "tv_")
    private Boolean tv;

    @Column(name = "fridge_")
    private Boolean fridge;

    @Column(name = "stove_")
    private Boolean stove;

    @Column(name = "washer_")
    private Boolean washer;

    @Column(name = "microwave_")
    private Boolean microwave;

    @Column(name = "wifi_")
    private Boolean wifi;

    @Column(name = "conditioner_")
    private Boolean conditioner;

    @Column(name = "brazier_")
    private Boolean brazier;

    @Column(name = "kazan_")
    private Boolean kazan;//казан



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id_", nullable = false)
    public UserEntity owner;

    @Column(name = "header_")
    private String header;

    @Column(name = "description_")
    private String description;

    @Column(name = "short_description_")
    private String shortDescription;

    @Column(name = "created_at_")
    private Date createdAt;

    @Column(name = "edited_at_")
    private Date editedAt;

    @Column(name = "housing_type_")
    private HousingType housingType;

    @Column(name = "summer_houses_count_")
    private Integer summerHousesCount;

    @Column(name = "views_num_")
    private Integer viewsNum;

    @Column(name = "sauna_")
    private Boolean sauna;

    @Column(name = "bathhouse_")
    private Boolean bathhouse;

    @Column(name = "restaurant_")
    private Boolean restaurant;

    @Column(name = "swimming_pool_")
    private Boolean swimmingPool;

    @Column(name = "billiards_")
    private Boolean billiards;

    @Column(name = "latitude_")
    private Double latitude;

    @Column(name = "longitude_")
    private Double longitude;

    @Column(name = "status_")
    private AdStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locality_id_")
    private LocalityEntity locality;

    @Column(name = "street_")
    private String street;

    @Column(name = "house_number_")
    private String houseNumber;

    @Column(name = "summer_kitchen_")
    private Boolean summerKitchen;//летняя кухня
    @Column(name = "cook_")
    private Boolean cook;//повар
    @Column(name = "alcove_")
    private Boolean alcove;//беседка
    @Column(name = "territory_area_")
    private Integer territoryArea;//площадь территории в сотках
    @Column(name = "tapchan_")
    private Boolean tapchan;//тапчан
    @Column(name = "bicycles_")
    private Boolean bicycles;//велосипеды
    @Column(name = "quad_bikes_")
    private Boolean quadBikes;//квадроциклы
    @Column(name = "playground_")
    private Boolean playground;//детская площадка
}
