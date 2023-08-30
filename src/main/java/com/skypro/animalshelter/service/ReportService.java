package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.exception.AnimalNotFoundException;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import com.skypro.animalshelter.exception.ReportNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface ReportService {
    SendMessage postReport(Long chatId, Update update);

    /**
     * Проверка на то, был ли отправлен сегодня отчет по животному
     * @return {@code true} если отчет сегодня был отправлен, {@code false} в противном случае
     */
    Boolean checkIsFullReportPostToday();

    /**
     * Создание нового отчета и сохранение его в базу данных <br>
     * @see JpaRepository#save(Object)
     * @param report объект класса {@link Report}, не может быть {@code null}
     * @return созданный объект класса {@link Report}
     */
    Report createReport(Report report);

    /**
     * Редактирование отчета и сохранение его в базу данных <br>
     * @see  JpaRepository#save(Object)
     * @param report объект класса {@link Report}, не может быть {@code null}
     * @return отредактированный объект класса {@link Report}
     * @throws  ReportNotFoundException если отчет не был найден в базе данных
     */
    Report editReport(Report report);

    /**
     * Поиск отчета в базе данных по его {@code id} <br>
     * @see JpaRepository#findById(Object)
     * @param id идентификатор искомого отчета, не может быть {@code null}
     * @throws ReportNotFoundException если отчет с указанным {@code id} не был найден в базе данных
     * @return найденный объект класса {@link Report}
     */
    Report findReportById(Long id);

    /**
     * Вывод списка всех отчетов из базы данных<br>
     * @see JpaRepository#findAll()
     * @return список объектов класса {@link Report}
     */
    List<Report> getAllReports();

    /**
     * Удаление отчета из базы данных по его {@code id} <br>
     * @see JpaRepository#deleteById(Object)
     * @param id идентификатор отчета, который нужно удалить из базы данных, не может быть {@code null}
     * @throws ReportNotFoundException если отчет с указанным {@code id} не был найден в базе данных
     * @return {@code true} если объект был найден в базе данных, в противном случае {@link ReportNotFoundException}
     */
    boolean deleteReportById(Long id);
}
