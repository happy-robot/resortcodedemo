package kz.kaps.resort.entrypoints.rest.pricetags;

import kz.kaps.resort.core.domain.PriceTag;
import kz.kaps.resort.core.usecase.pricetag.GetPriceTagsByAdBetweenDatesUseCase;
import kz.kaps.resort.entrypoints.html.PriceTagDto;
import kz.kaps.resort.entrypoints.html.PriceTagDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Validated
public class GetPriceTagsByAdBetweenDatesEndPoint {

    private GetPriceTagsByAdBetweenDatesUseCase getPriceTagsByAdBetweenDatesUseCase;

    @Autowired
    public GetPriceTagsByAdBetweenDatesEndPoint(GetPriceTagsByAdBetweenDatesUseCase getPriceTagsByAdBetweenDatesUseCase){
        this.getPriceTagsByAdBetweenDatesUseCase = getPriceTagsByAdBetweenDatesUseCase;
    }

    @GetMapping("/api/rest/v1/pricetags")
    public Set<PriceTagDto> getPriceTagsByAdBetweenDatesEndPoint(@RequestParam(value = "adId", required = true) Long adId,
                                                                 @RequestParam(value = "startDate", required = true)
                                                                 @Pattern(regexp="[0-9]{2}.[0-9]{2}.[0-9]{4}") String startDate,
                                                                 @RequestParam(value = "endDate", required = true)
                                                                     @Pattern(regexp="[0-9]{2}.[0-9]{2}.[0-9]{4}") String endDate){

        Set<PriceTag> priceTags = getPriceTagsByAdBetweenDatesUseCase.getPriceTagsByAdBetweenDatesUseCase(adId, startDate, endDate);
        Set<PriceTagDto> priceTagDtos = priceTags.stream().map(e -> PriceTagDtoConverter.toDto(e)).collect(Collectors.toSet());
        return priceTagDtos;
    }

}
