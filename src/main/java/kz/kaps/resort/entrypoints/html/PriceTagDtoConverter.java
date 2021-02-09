package kz.kaps.resort.entrypoints.html;

import kz.kaps.resort.core.domain.PriceTag;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PriceTagDtoConverter {

    public static PriceTag fromDto(PriceTagDto priceTagDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        PriceTag priceTag = PriceTag.builder()
                .id(priceTagDto.getId())
                .price(priceTagDto.getPrice())
                .startDate(LocalDate.parse(priceTagDto.getStartDate(), formatter))
                .endDate(LocalDate.parse(priceTagDto.getEndDate(), formatter))
                .build();
        return priceTag;
    }

    public static PriceTagDto toDto(PriceTag priceTag){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        PriceTagDto priceTagDto = PriceTagDto.builder()
                .id(priceTag.getId())
                .price(priceTag.getPrice())
                .startDate(priceTag.getStartDate().format(formatter))
                .endDate(priceTag.getEndDate().format(formatter))
                .build();
        return priceTagDto;
    }

}
