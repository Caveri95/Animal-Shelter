package com.skypro.animalshelter.model;

public enum Shelter {

    SHELTER_INFO("Тут общая информация о приюте"),
    SHELTER_CONTACT("Расписание, маршрут, схема проезда (надо подумать как прикрутить)"),
    SHELTER_SAFETY_INFO("Общие рекомендации о технике безопасности на территории приюта"),
    SHELTER_CAR_PASS("Оформление пропуска на машину");


    private final String description;

    Shelter(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
