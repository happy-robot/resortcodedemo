package kz.kaps.resort.entrypoints.rest.tenant;

import kz.kaps.resort.core.usecase.ad.tenant.FilterDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class SearchResponseDto {

    Page<AdForSearchDto> cards;
    List<FilterDto> filters;
    private Integer startPrice;
    private Integer endPrice;
    private Integer startRoomNum;
    private Integer endRoomNum;
}
