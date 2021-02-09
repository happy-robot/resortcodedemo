package kz.kaps.resort.core.usecase.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LocaleUtils {

    public static final String MSG_CODE_FLAT_HEADER = "common.flat.header";
    public static final String MSG_CODE_HOUSE_HEADER = "common.house.header";
    public static final String MSG_CODE_COTTAGE_HEADER = "common.cottage.header";
    public static final String MSG_CODE_SUMMER_HOUSES_HEADER = "common.summer-houses.header";
    public static final String MSG_CODE_PLURAL_SUMMER_HOUSES = "plural.SUMMER_HOUSE";
    public static final String MSG_CODE_COMMON_PRICE_PER_DAY_TG = "common.price.per.day.tg";
    public static final String MSG_CODE_COMMON_PRICE_PER_DAY_PER_PERSON_TG = "common.price.per.day.per.person.tg";
    public static final String MSG_CODE_COMMON_TG = "common.tg";

    public static final String MSG_CODE_COMMON_LOCALITY = "common.locality";
    public static final String MSG_CODE_COMMON_HOUSING_TYPE = "common.housing.type";
    public static final String MSG_CODE_COMMON_FACILITIES = "common.facilities";
    public static final String MSG_CODE_COMMON_HOT_WATER = "common.hot-water";
    public static final String MSG_CODE_COMMON_COLD_WATER = "common.cold-water";
    public static final String MSG_CODE_COMMON_SAUNA = "common.sauna";
    public static final String MSG_CODE_COMMON_BATHHOUSE = "common.bathhouse";
    public static final String MSG_CODE_COMMON_ALCOVE = "common.alcove";
    public static final String MSG_CODE_COMMON_TAPCHAN = "common.tapchan";
    public static final String MSG_CODE_COMMON_BRAZIER = "common.brazier";
    public static final String MSG_CODE_COMMON_KAZAN = "common.kazan";
    public static final String MSG_CODE_COMMON_SUMMER_KITCHEN = "common.summerKitchen";
    public static final String MSG_CODE_COMMON_PLAYGROUND = "common.playground";
    public static final String MSG_CODE_COMMON_BICYCLES = "common.bicycles";
    public static final String MSG_CODE_COMMON_QUAD_BIKES = "common.quadBikes";
    public static final String MSG_CODE_COMMON_BILLIARDS = "common.billiards";
    public static final String MSG_CODE_COMMON_SWIMMING_POOL = "common.swimmingPool";
    public static final String MSG_CODE_COMMON_COOK = "common.cook";
    public static final String MSG_CODE_COMMON_TECHNICS = "common.technics";
    public static final String MSG_CODE_COMMON_STOVE = "common.stove";
    public static final String MSG_CODE_COMMON_FRIDGE = "common.fridge";
    public static final String MSG_CODE_COMMON_MICROWAVE = "common.microwave";
    public static final String MSG_CODE_COMMON_WIFI = "common.wifi";
    public static final String MSG_CODE_COMMON_TV = "common.tv";
    public static final String MSG_CODE_COMMON_WASHER = "common.washer";
    public static final String MSG_CODE_COMMON_CONDITIONER = "common.conditioner";

    private static MessageSource injectedMessageSource;

    private LocaleUtils(){}

    public static Locale getLocale() {
        Locale locale = Locale.getDefault();
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
            if (request.getLocale() != null) {
                locale = request.getLocale();
            }
        }
        return locale;
    }

    public static String getLocalized(String valueRu, String valueEn) {

        switch (getLocale().getLanguage()) {
            case "ru":
                return valueRu;
            case "en":
                return valueEn;
            default:
                return valueRu;
        }

    }

    public static String getMessage(String messageKey, Locale locale, Object... arguments) {
        return getInjectedMessageSource().getMessage(messageKey, arguments, messageKey, locale);
    }

    private static MessageSource getInjectedMessageSource() {
        if (injectedMessageSource == null) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("messages");
            messageSource.setDefaultEncoding("UTF-8");
            injectedMessageSource = messageSource;
        }
        return injectedMessageSource;
    }
}
