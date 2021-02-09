package kz.kaps.resort.core.dictionaries;

import kz.kaps.resort.core.usecase.utils.LocaleUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Locality {

    private Long id;

    private String nameRu;

    private String nameEn;

    private Country country;

    private String imageFileNameWithExt;

    private String imageUrl;

    public Locality(Long id) {
        this.id = id;
    }

    public String getName() {
        return LocaleUtils.getLocalized(nameRu, nameEn);
    }

}
