package kz.kaps.resort.core.usecase.ad.tenant;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.HousingType;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class FacetedSearchFormDto {

    public static final String ATTRIBUTE_HOUSING_TYPE = "housingType";
    public static final String ATTRIBUTE_LOCALITY_IDS = "localityId";

    public static final String ATTRIBUTE_START_PRICE = "startPrice";
    public static final String ATTRIBUTE_END_PRICE = "endPrice";

    public static final String ATTRIBUTE_START_ROOM_NUM = "startRoomNum";
    public static final String ATTRIBUTE_END_ROOM_NUM = "endRoomNum";

    public static final String ATTRIBUTE_HOT_WATER = "hotWater";
    public static final String ATTRIBUTE_COLD_WATER = "coldWater";
    public static final String ATTRIBUTE_TV = "tv";
    public static final String ATTRIBUTE_FRIDGE = "fridge";
    public static final String ATTRIBUTE_STOVE = "stove";
    public static final String ATTRIBUTE_WASHER = "washer";
    public static final String ATTRIBUTE_MICROWAVE = "microwave";
    public static final String ATTRIBUTE_WIFI = "wifi";
    public static final String ATTRIBUTE_BRAZIER = "brazier";
    public static final String ATTRIBUTE_KAZAN = "kazan";
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
    public static final String ATTRIBUTE_CONDITIONER = "conditioner";

    public FacetedSearchFormDto() { }

    public FacetedSearchFormDto(String query) {
        Map<String, List<String>> parameters = getParams(query);
        fillHousingType(parameters);
        fillStartPrice(parameters);
        fillEndPrice(parameters);
        fillStartRoomNum(parameters);
        fillEndRoomNum(parameters);
        fillHotWater(parameters);
        fillColdWater(parameters);
        fillTv(parameters);
        fillFridge(parameters);
        fillStove(parameters);
        fillWasher(parameters);
        fillMicrowave(parameters);
        fillWifi(parameters);
        fillConditioner(parameters);
        fillBrazier(parameters);
        fillKazan(parameters);
        fillSauna(parameters);
        fillBathhouse(parameters);
        fillRestaurant(parameters);
        fillSwimmingPool(parameters);
        fillBilliards(parameters);
        fillSummerKitchen(parameters);
        fillCook(parameters);
        fillAlcove(parameters);
        fillTapchan(parameters);
        fillBicycles(parameters);
        fillQuadBikes(parameters);
        fillPlayground(parameters);
        fillLocalitiesIds(parameters);
    }

    private Integer startPrice;
    private Integer endPrice;

    private List<HousingType> housingType = new ArrayList<>();
    private List<Long> localityId = new ArrayList<>();
    private Integer startRoomNum;
    private Integer endRoomNum;

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
    private Boolean tapchan;//тапчан
    private Boolean bicycles;//велосипеды
    private Boolean quadBikes;//квадроциклы
    private Boolean playground;//детская площадка

    private AdStatusEnum status;

    private void fillHousingType(Map<String, List<String>> params) {
        List<String> paramList = params.get(FacetedSearchFormDto.ATTRIBUTE_HOUSING_TYPE);
        if(paramList != null) {
            List<HousingType> enumParams = paramList.stream().map(p -> {
                try {
                    return HousingType.valueOf(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }).filter(p -> p != null).collect(Collectors.toList());
            this.housingType.addAll(enumParams);
        }
    }

    private Map<String, List<String>> getParams(String query) {
        if(query == null) return Collections.emptyMap();
        String[] parts = query.split(":");
        Map<String, List<String>> map = new HashMap<>();
        for(int i = 0; i < parts.length; i++) {
            if(i % 2 == 0 && i != 0) {
                List<String> val = null;
                if(map.containsKey(parts[i - 1].trim())) {
                    val = map.get(parts[i - 1].trim());
                } else {
                    val = new ArrayList<>();
                }
                val.add(parts[i].trim());
                map.put(parts[i - 1].trim(), val);
            }
        }
        return map;
    }

    private void fillStartPrice(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_START_PRICE);
        if(val != null && !val.isEmpty()) {
            this.startPrice = Integer.valueOf(val.get(0));
        }
    }

    private void fillEndPrice(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_END_PRICE);
        if(val != null && !val.isEmpty()) {
            this.endPrice = Integer.valueOf(val.get(0));
        }
    }

    private void fillStartRoomNum(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_START_ROOM_NUM);
        if(val != null && !val.isEmpty()) {
            this.startRoomNum = Integer.valueOf(val.get(0));
        }
    }

    private void fillEndRoomNum(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_END_ROOM_NUM);
        if(val != null && !val.isEmpty()) {
            this.endRoomNum = Integer.valueOf(val.get(0));
        }
    }

    private void fillHotWater(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_HOT_WATER);
        if(val != null && !val.isEmpty()) {
            this.hotWater = Boolean.valueOf(val.get(0));
        }
    }

    private void fillColdWater(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_COLD_WATER);
        if(val != null && !val.isEmpty()) {
            this.coldWater = Boolean.valueOf(val.get(0));
        }
    }

    private void fillTv(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_TV);
        if(val != null && !val.isEmpty()) {
            this.tv = Boolean.valueOf(val.get(0));
        }
    }

    private void fillFridge(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_FRIDGE);
        if(val != null && !val.isEmpty()) {
            this.fridge = Boolean.valueOf(val.get(0));
        }
    }

    private void fillStove(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_STOVE);
        if(val != null && !val.isEmpty()) {
            this.stove = Boolean.valueOf(val.get(0));
        }
    }

    private void fillWasher(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_WASHER);
        if(val != null && !val.isEmpty()) {
            this.washer = Boolean.valueOf(val.get(0));
        }
    }

    private void fillMicrowave(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_MICROWAVE);
        if(val != null && !val.isEmpty()) {
            this.microwave = Boolean.valueOf(val.get(0));
        }
    }

    private void fillWifi(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_WIFI);
        if(val != null && !val.isEmpty()) {
            this.wifi = Boolean.valueOf(val.get(0));
        }
    }

    private void fillConditioner(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_CONDITIONER);
        if(val != null && !val.isEmpty()) {
            this.conditioner = Boolean.valueOf(val.get(0));
        }
    }

    private void fillBrazier(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_BRAZIER);
        if(val != null && !val.isEmpty()) {
            this.brazier = Boolean.valueOf(val.get(0));
        }
    }

    private void fillKazan(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_KAZAN);
        if(val != null && !val.isEmpty()) {
            this.kazan = Boolean.valueOf(val.get(0));
        }
    }

    private void fillSauna(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_SAUNA);
        if(val != null && !val.isEmpty()) {
            this.sauna = Boolean.valueOf(val.get(0));
        }
    }

    private void fillBathhouse(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_BATHHOUSE);
        if(val != null && !val.isEmpty()) {
            this.bathhouse = Boolean.valueOf(val.get(0));
        }
    }

    private void fillRestaurant(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_RESTAURANT);
        if(val != null && !val.isEmpty()) {
            this.restaurant = Boolean.valueOf(val.get(0));
        }
    }

    private void fillSwimmingPool(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_SWIMMING_POOL);
        if(val != null && !val.isEmpty()) {
            this.swimmingPool = Boolean.valueOf(val.get(0));
        }
    }

    private void fillBilliards(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_BILLIARDS);
        if(val != null && !val.isEmpty()) {
            this.billiards = Boolean.valueOf(val.get(0));
        }
    }

    private void fillSummerKitchen(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_SUMMER_KITCHEN);
        if(val != null && !val.isEmpty()) {
            this.summerKitchen = Boolean.valueOf(val.get(0));
        }
    }

    private void fillCook(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_COOK);
        if(val != null && !val.isEmpty()) {
            this.cook = Boolean.valueOf(val.get(0));
        }
    }

    private void fillAlcove(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_ALCOVE);
        if(val != null && !val.isEmpty()) {
            this.alcove = Boolean.valueOf(val.get(0));
        }
    }

    private void fillTapchan(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_TAPCHAN);
        if(val != null && !val.isEmpty()) {
            this.tapchan = Boolean.valueOf(val.get(0));
        }
    }

    private void fillBicycles(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_BYCICLES);
        if(val != null && !val.isEmpty()) {
            this.bicycles = Boolean.valueOf(val.get(0));
        }
    }

    private void fillQuadBikes(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_QUAD_BIKES);
        if(val != null && !val.isEmpty()) {
            this.quadBikes = Boolean.valueOf(val.get(0));
        }
    }

    private void fillPlayground(Map<String, List<String>> params) {
        List<String> val = params.get(FacetedSearchFormDto.ATTRIBUTE_PLAYGROUND);
        if(val != null && !val.isEmpty()) {
            this.playground = Boolean.valueOf(val.get(0));
        }
    }

    private void fillLocalitiesIds(Map<String, List<String>> params) {
        List<String> paramList = params.get(FacetedSearchFormDto.ATTRIBUTE_LOCALITY_IDS);
        if(paramList != null) {
            List<Long> ids = paramList.stream().map(p -> {
                try {
                    return Long.valueOf(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }).filter(p -> p != null).collect(Collectors.toList());
            this.localityId.addAll(ids);
        }
    }

    public boolean containsLocalityId(Long id) {
        if(localityId == null) return false;
        return localityId.contains(id);
    }

    public boolean containsHousingType(HousingType housingType) {
        if(this.housingType == null) return false;
        return this.housingType.contains(housingType);
    }

}
