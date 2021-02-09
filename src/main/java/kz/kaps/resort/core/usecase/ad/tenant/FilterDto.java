package kz.kaps.resort.core.usecase.ad.tenant;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilterDto {

    private Boolean active = false;
    private String id;
    private Boolean multiselect = true;
    private String title;
    private List<FilterRowDto> rows;

}
