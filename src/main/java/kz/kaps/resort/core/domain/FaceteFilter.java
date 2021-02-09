package kz.kaps.resort.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FaceteFilter {

    private String arriveDate;
    private String departDate;

    private Integer fromPrice;
    private Integer toPrice;

    private Boolean flat;
    private Boolean cottage;
    private Boolean summerHouse;
    private Boolean house;
    private Boolean hotel;
    private Boolean sanatorium;
    private Boolean recreationCenter;
    private Boolean guestHouse;

    private Integer fromRoomNum;
    private Integer toRoomNum;

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
    private Boolean restaurant;
    private Boolean swimmingPool;

    private Long housingNumber;

}
