package com.skypro.animalshelter.exception;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException() {
        super("Отчет не найден");
    }
}
