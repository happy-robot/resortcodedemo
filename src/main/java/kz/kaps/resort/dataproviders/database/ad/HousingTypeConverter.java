package kz.kaps.resort.dataproviders.database.ad;

import kz.kaps.resort.core.domain.HousingType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class HousingTypeConverter implements AttributeConverter<HousingType, String> {

    @Override
    public String convertToDatabaseColumn(HousingType housingType) {
        if (housingType == null) {
            return null;
        }
        return housingType.getCode();
    }

    @Override
    public HousingType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(HousingType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
