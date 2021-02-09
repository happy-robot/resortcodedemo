package kz.kaps.resort.entrypoints.html.tenant.dto;

import kz.kaps.resort.entrypoints.html.AdDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdViewDto {

    private String header;
    private AdDto ad;
    private String weekdayPrice;
    private String holidayPrice;

}
