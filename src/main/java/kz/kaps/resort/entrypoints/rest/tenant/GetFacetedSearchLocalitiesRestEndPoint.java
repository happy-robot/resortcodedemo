package kz.kaps.resort.entrypoints.rest.tenant;

import kz.kaps.resort.core.usecase.ad.GetFacetedSearchLocalitiesUseCase;
import kz.kaps.resort.entrypoints.html.landlord.dto.LocalityDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GetFacetedSearchLocalitiesRestEndPoint {

    private GetFacetedSearchLocalitiesUseCase getFacetedSearchLocalitiesUseCase;
    private ModelMapper modelMapper;

    @Autowired
    public GetFacetedSearchLocalitiesRestEndPoint(
            GetFacetedSearchLocalitiesUseCase getFacetedSearchLocalitiesUseCase, ModelMapper modelMapper) {
        this.getFacetedSearchLocalitiesUseCase = getFacetedSearchLocalitiesUseCase;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/rest/v1/localities/faceted-search")
    public List<LocalityDto> getLocalities() {
        return getFacetedSearchLocalitiesUseCase.getFacetedSearchLocalities()
                .stream().map(s -> modelMapper.map(s, LocalityDto.class)).collect(Collectors.toList());
    }
}
