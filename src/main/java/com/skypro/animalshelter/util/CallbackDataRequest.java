package com.skypro.animalshelter.util;

public enum CallbackDataRequest {

    CAT("Приют для котов", "CAT"),
    DOG("Приют для собак", "DOG"),
    GENERAL_SHELTER_INFO("Информация о приюте", "GENERAL_SHELTER_INFO"),
    HOW_TO_TAKE_ANIMAL("Как взять животное с приюта", "HOW_TO_TAKE_ANIMAL"),
    REPORT_ANIMAL("Прислать отчет о питомце", "REPORT_ANIMAL"),
    VOLUNTEER("Позвать волонтера", "VOLUNTEER"),
    ABOUT_SHELTER("О нас", "ABOUT_SHELTER"),
    CONTACT_SHELTER("Наши контакты", "CONTACT_SHELTER"),
    SAFETY_CONTACT_FOR_CAR_PASS("Оформить пропуск на машину", "SAFETY_CONTACT_FOR_CAR_PASS"),
    SAFETY_IN_SHELTER_TERRITORY("Техника безопасности на территории приюта", "SAFETY_IN_SHELTER_TERRITORY"),
    GIVE_MY_CONTACT("Оставить контакт для связи", "GIVE_MY_CONTACT"),
    CALL_VOLUNTEER("Позвать волонтера", "CALL_VOLUNTEER"),
    ROLLBACK("Вернуться назад", "ROLLBACK"),

    //Информация приюта
    SHELTER_INFO("Тут общая информация о приюте", "SHELTER_INFO"),
    SHELTER_CONTACT("Расписание, маршрут, схема проезда (надо подумать как прикрутить)", "SHELTER_CONTACT"),
    SHELTER_SAFETY_INFO("Общие рекомендации о технике безопасности на территории приюта", "SHELTER_SAFETY_INFO"),
    SHELTER_CAR_PASS("Оформление пропуска на машину", "SHELTER_CAR_PASS");


    private final String text;
    private final String callbackData;

    CallbackDataRequest(String text, String callbackData) {
        this.text = text;
        this.callbackData = callbackData;
    }

    public String getText() {
        return text;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public static CallbackDataRequest getConstantByRequest(String data) {
        for (CallbackDataRequest value : CallbackDataRequest.values()) {
            if (value.getCallbackData().equals(data)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Передан неизвестный аргумент");
    }
}
