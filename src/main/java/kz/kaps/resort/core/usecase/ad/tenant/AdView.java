package kz.kaps.resort.core.usecase.ad.tenant;

import kz.kaps.resort.core.domain.Ad;
import lombok.Data;


@Data
public class AdView {

    private String header;
    private String weekdayPrice;
    private String holidayPrice;

    private Ad ad;

}
