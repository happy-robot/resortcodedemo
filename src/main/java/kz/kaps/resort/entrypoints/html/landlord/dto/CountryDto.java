package kz.kaps.resort.entrypoints.html.landlord.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CountryDto {

    private Long id;

    private String nameRu;

    private String nameEn;

    private List<LocalityDto> localities;

}
