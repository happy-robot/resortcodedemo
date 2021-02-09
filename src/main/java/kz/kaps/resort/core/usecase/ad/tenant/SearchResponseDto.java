package kz.kaps.resort.core.usecase.ad.tenant;

import kz.kaps.resort.core.domain.AdForSearch;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class SearchResponseDto {

    Page<AdForSearch> cards;
    List<FilterDto> filters;
    private Integer startPrice;
    private Integer endPrice;
    private Integer startRoomNum;
    private Integer endRoomNum;

}
