package kz.kaps.resort.core.domain;

import lombok.Data;

/**
 * Модель для результатов фасетного поиска
 */
@Data
public class AdForSearch {

    private String header;
    private String prices;
    private String imageUrlForMediumSize;
    private String localityName;

    public AdForSearch(Ad ad) {
        id = ad.getId();
        isPricePerPerson = ad.getIsPricePerPerson();
        weekdayPricePerDay = ad.getWeekdayPricePerDay();
        holidayPricePerDay = ad.getHolidayPricePerDay();
        housingType = ad.getHousingType();
        roomNum = ad.getRoomNum();
        floor = ad.getFloor();
        floorNum = ad.getFloorNum();
        sleepNum = ad.getSleepNum();
        area = ad.getArea();
        hotWater = ad.getHotWater();
        coldWater = ad.getColdWater();
        tv = ad.getTv();
        fridge = ad.getFridge();
        stove = ad.getStove();
        washer = ad.getWasher();
        microwave = ad.getMicrowave();
        wifi = ad.getWifi();
        conditioner = ad.getConditioner();
        brazier = ad.getBrazier();
        kazan = ad.getKazan();
        sauna = ad.getSauna();
        bathhouse = ad.getBathhouse();
        restaurant = ad.getRestaurant();
        swimmingPool = ad.getSwimmingPool();
        billiards = ad.getBilliards();
        summerKitchen = ad.getSummerKitchen();
        cook = ad.getCook();
        alcove = ad.getAlcove();
        area = ad.getArea();
        territoryArea = ad.getTerritoryArea();
        tapchan = ad.getTapchan();
        bicycles = ad.getBicycles();
        quadBikes = ad.getQuadBikes();
        playground = ad.getPlayground();
    }

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
