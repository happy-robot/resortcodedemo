package kz.kaps.resort.entrypoints.converters;

import kz.kaps.resort.core.domain.HousingType;
import org.springframework.core.convert.converter.Converter;

public class HousingTypeConverter implements Converter<String, HousingType> {
    @Override
    public HousingType convert(String s) {
        return HousingType.valueOf(s.toUpperCase());
    }
}
