package kz.kaps.resort.core.dictionaries;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Country {

    private Long id;

    private String nameRu;

    private String nameEn;

    private List<Locality> localities;

}
