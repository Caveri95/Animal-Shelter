package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface ReportService {
    SendMessage postReport(Long chatId, Update update);

    Boolean checkIsFullReportPostToday();
}
