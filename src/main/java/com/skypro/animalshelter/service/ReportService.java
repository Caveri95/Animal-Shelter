package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.Report;

import java.util.List;

public interface ReportService {
    SendMessage postReport(Long chatId, Update update);

    Boolean checkIsFullReportPostToday();

    Report createReport(Report report);

    Report editReport(Report report);

    Report findReportById(Long id);

    List<Report> getAllReports();

    boolean deleteReportById(Long id);
}
