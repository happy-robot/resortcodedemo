package kz.kaps.resort.entrypoints.html.landlord.validators;

import kz.kaps.resort.core.domain.HousingType;
import kz.kaps.resort.entrypoints.html.landlord.dto.AdFormDto;

import javax.validation.*;
import java.util.Set;

public class AdFormValidator implements ConstraintValidator<AdFormValid, AdFormDto> {

    private static final String FLOOR_MUST_BE_LESS_THAN_FLOORS_NUM = "{validator.flat.floor.ls.building.floors}";
    private static final String FLOORS_NUM_MUST_BE_GREATER_THAN_FLOOR = "{validator.building.floors.gt.flat.floor}";

    public boolean isValid(AdFormDto adFormDto, ConstraintValidatorContext context) {
        boolean isValid = true;
        Class<?> validateGroups = getValidationGroup(adFormDto.getHousingType());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        final Set<ConstraintViolation<AdFormDto>> validate = validator.validate(adFormDto, validateGroups);
        if (!validate.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<AdFormDto> constraintViolation : validate) {
                sb.append(constraintViolation.getMessage());

                context.buildConstraintViolationWithTemplate(constraintViolation.getMessage())
                        .addPropertyNode(constraintViolation.getPropertyPath().toString())
                        .addConstraintViolation();
            }
            isValid = false;
        }
        return isValid && isFloorValid(adFormDto, context);
    }

    private Class<?> getValidationGroup(HousingType housingType) {
        switch (housingType) {
            case SUMMER_HOUSE: return AdFormDto.SummerhouseGroup.class;
            case COTTAGE: return AdFormDto.CottageGroup.class;
            case HOUSE: return AdFormDto.HouseGroup.class;
            case FLAT: return AdFormDto.FlatGroup.class;
            default: return null;
        }
    }

    private boolean isFloorValid(AdFormDto adFormDto, ConstraintValidatorContext context) {
        boolean isValid = true;
        if(adFormDto.getFloor() != null && adFormDto.getFloorNum() != null
                && adFormDto.getFloor() > adFormDto.getFloorNum()) {
            context.buildConstraintViolationWithTemplate(FLOOR_MUST_BE_LESS_THAN_FLOORS_NUM)
                    .addPropertyNode(AdFormDto.ATTRIBUTE_FLOOR)
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate(FLOORS_NUM_MUST_BE_GREATER_THAN_FLOOR)
                    .addPropertyNode(AdFormDto.ATTRIBUTE_FLOOR_NUM)
                    .addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }

}
