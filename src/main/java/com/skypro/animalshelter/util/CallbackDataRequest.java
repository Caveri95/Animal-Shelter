package com.skypro.animalshelter.util;

public enum CallbackDataRequest {

    CAT("Приют для котов", "CAT"),
    TAKE_CAT("Взять кота", "TAKE_CAT"),
    TAKE_DOG("Взять собаку", "TAKE_DOG"),
    DOG("Приют для собак", "DOG"),
    GENERAL_SHELTER_INFO("Информация о приюте", "GENERAL_SHELTER_INFO"),
    HOW_TO_TAKE_ANIMAL("Как взять животное из приюта", "HOW_TO_TAKE_ANIMAL"),
    REPORT_ANIMAL("Прислать отчет о питомце", "REPORT_ANIMAL"),
    VOLUNTEER("Позвать волонтера", "VOLUNTEER"),
    ABOUT_SHELTER("О нас", "ABOUT_SHELTER"),
    CONTACT_SHELTER("Наши контакты", "CONTACT_SHELTER"),
    SAFETY_CONTACT_FOR_CAR_PASS("Оформить пропуск на машину", "SAFETY_CONTACT_FOR_CAR_PASS"),
    SAFETY_IN_SHELTER_TERRITORY("Техника безопасности на территории приюта", "SAFETY_IN_SHELTER_TERRITORY"),
    GIVE_MY_CONTACT("Оставить контакт для связи", "GIVE_MY_CONTACT"),
    CALL_VOLUNTEER("Позвать волонтера", "CALL_VOLUNTEER"),
    ROLLBACK("Вернуться назад", "ROLLBACK"),
    //Консультация клиента

    SHELTER_RULES_BEFORE_MEETING_ANIMAL("Правила знакомства с животным", "SHELTER_RULES_BEFORE_MEETING_ANIMAL"),
    DOCUMENTS_TO_TAKE_ANIMAL("Список документов, чтобы взять животное", "DOCUMENTS_TO_TAKE_ANIMAL"),
    TRANSPORTATION_ADVICE("Рекомендации по транспортировке животного", "TRANSPORTATION_ADVICE"),
    HOUSE_RULES_FOR_SMALL_ANIMAL("Рекомендации по обустройству дома для детенышей", "HOUSE_RULES_FOR_KITTY"),
    HOUSE_RULES_FOR_ADULT_ANIMAL("Рекомендации по обустройству дома взрослого животного", "HOUSE_RULES_FOR_ADULT_ANIMAL"),
    HOUSE_RULES_FOR_ANIMAL_WITH_DISABILITY("Уход за животными с ограниченными возможностями", "HOUSE_RULES_FOR_ANIMAL_WITH_DISABILITY"),
    CYNOLOGIST_ADVICE("Рекомендации по уходу от кинологов", "CYNOLOGIST_ADVICE"),
    CYNOLOGISTS("Рекомендованные кинологи", "CYNOLOGISTS"),
    REFUSE_REASONS("Причины для отказа в усыновлении собаки", "REFUSE_REASONS");

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
