package kz.kaps.resort.dataproviders.database.ad;

import kz.kaps.resort.core.domain.AdStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AdStatusEnumConverter implements AttributeConverter<AdStatusEnum, String> {


    @Override
    public String convertToDatabaseColumn(AdStatusEnum adStatusEnum) {
        if (adStatusEnum == null) {
            return null;
        }
        return adStatusEnum.getCode();
    }

    @Override
    public AdStatusEnum convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(AdStatusEnum.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
