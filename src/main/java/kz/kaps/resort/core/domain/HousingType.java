package kz.kaps.resort.core.domain;

import kz.kaps.resort.core.usecase.utils.LocaleUtils;

public enum HousingType {

    //квартира
    FLAT("FLT", "single.FLAT"),

    //коттедж
    COTTAGE("CTG", "single.COTTAGE"),

    //летний домик
    SUMMER_HOUSE("SMRHS", "single.SUMMER_HOUSE"),

    //частный дом
    HOUSE("HS", "single.HOUSE"),

    //гостиница
    HOTEL("HTL", "single.HOTEL"),

    //санаторий
    SANATORIUM("SNTR", "single.SANATORIUM"),

    //база отдыха
    RECREATION_CENTER("RCRCNTR", "single.RECREATION_CENTER"),

    //гостевой дом
    GUEST_HOUSE("GHS", "single.GUEST_HOUSE");

    private String code;
    private String localizedCode;

    HousingType(String code, String localizedCode){
        this.code = code;
        this.localizedCode = localizedCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return LocaleUtils.getMessage(localizedCode, LocaleUtils.getLocale(), (Object)null);
    }
}
