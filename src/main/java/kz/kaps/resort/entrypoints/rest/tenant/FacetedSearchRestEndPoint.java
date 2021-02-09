package kz.kaps.resort.entrypoints.rest.tenant;

import kz.kaps.resort.core.usecase.ad.tenant.FacetedSearchUseCaseV2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class FacetedSearchRestEndPoint {

    private FacetedSearchUseCaseV2 facetedSearchUseCase;
    private ModelMapper modelMapper;

    public FacetedSearchRestEndPoint(FacetedSearchUseCaseV2 facetedSearchUseCase, ModelMapper modelMapper) {
        this.facetedSearchUseCase = facetedSearchUseCase;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/rest/v1/search")
    public SearchResponseDto search(@RequestParam(value = "q", required = false) String q, Pageable pageable) {
        kz.kaps.resort.core.usecase.ad.tenant.SearchResponseDto page = facetedSearchUseCase.facetedSearch(q, pageable);
        Page<AdForSearchDto> adForSearchDtos = new PageImpl<>(
                page.getCards().getContent().stream().map(s -> {
                    AdForSearchDto dto = modelMapper.map(s, AdForSearchDto.class);
                    dto.setUrlLink("/ads/" + dto.getId());
                    return dto;
                }).collect(Collectors.toList()),
                pageable, page.getCards().getTotalElements()
        );
        SearchResponseDto searchResponseDto = modelMapper.map(page, SearchResponseDto.class);
        searchResponseDto.setCards(adForSearchDtos);
        return searchResponseDto;
    }

}
