package com.skypro.animalshelter.exception;

public class ShelterUserNotFoundException extends RuntimeException {

    public ShelterUserNotFoundException() {
        super("Пользователь не найден");
    }
}
