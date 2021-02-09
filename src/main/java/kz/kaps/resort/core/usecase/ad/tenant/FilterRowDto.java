package kz.kaps.resort.core.usecase.ad.tenant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterRowDto {

    private Boolean active = false;
    private Integer count;
    private Boolean hide = false;
    private String id;
    private String title;

}
