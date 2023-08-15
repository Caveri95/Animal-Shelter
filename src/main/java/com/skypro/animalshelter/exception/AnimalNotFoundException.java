package com.skypro.animalshelter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Такое животное не найдено")
public class AnimalNotFoundException extends RuntimeException {

    public AnimalNotFoundException() {
        super("Животное не найдено!");
    }
}
