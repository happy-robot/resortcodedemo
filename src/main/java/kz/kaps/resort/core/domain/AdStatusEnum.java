package kz.kaps.resort.core.domain;

public enum AdStatusEnum {

    //создается как только пользователь открыл страницу создания объявления
    //То есть Ad уже будет в базе до нажатия кнопки Сохранить
    SEED("S"),

    DRAFT("D"),

    MODERATION("M"),

    //Отклонен модератором
    REJECTED("R"),

    PUBLISHED("P"),

    ARCHIVE("A");

    private String code;

    private AdStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
