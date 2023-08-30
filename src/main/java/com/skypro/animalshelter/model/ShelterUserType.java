package com.skypro.animalshelter.model;

public enum ShelterUserType {

    JUST_LOOKING("Просто смотрит"),
    PROBATION("Находится на испытательном сроке"),
    PROBATION_EXTEND_14("Испытательный срок продлен на 14 дней"),
    PROBATION_EXTEND_30("Испытательный срок продлен на 30 дней"),
    SUCCESSFUL_COMPLETION("Успешно прошел испытательный срок"),
    FAILED("Испытательный срок не пройден");

    private final String type;

    ShelterUserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
