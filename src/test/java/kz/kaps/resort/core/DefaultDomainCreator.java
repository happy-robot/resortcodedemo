package kz.kaps.resort.core;

import kz.kaps.resort.core.domain.*;
import kz.kaps.resort.entrypoints.html.landlord.dto.AdFormDto;

public class DefaultDomainCreator {

    public static User getUser() {
        User user = new User("username", "password");
        user.setId(1L);
        return user;
    }

    public static Ad geAd() {
        return Ad.builder().id(1L).build();
    }

    public static Ad geAdFlat() {
        return Ad.builder().id(1L).housingType(HousingType.FLAT).build();
    }

    public static AdFormDto geAdFormDto() {
        AdFormDto adFormDto = new AdFormDto();
        adFormDto.setId(1L);
        return adFormDto;
    }

}
