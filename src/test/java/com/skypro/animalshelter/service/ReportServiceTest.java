package com.skypro.animalshelter.service;

import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.Report;
import com.skypro.animalshelter.model.ShelterUser;
import com.skypro.animalshelter.repository.ReportRepository;
import com.skypro.animalshelter.service.impl.AnimalServiceImpl;
import com.skypro.animalshelter.service.impl.ReportServiceImpl;
import liquibase.pro.packaged.R;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;
    @InjectMocks
    private ReportServiceImpl reportService;

    static Animal animal = new Animal(1L, "CAT", "Британец", true);
    static ShelterUser user = new ShelterUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal);

    Report report = new Report(1L, "photoPath", LocalDate.now(), "textPhoto", user);


    public static final List<Report> REPORT_LIST = List.of(
            new Report(1L, "photoPath", LocalDate.now(), "textPhoto", user),
            new Report(1L, "photoPath", LocalDate.now(), "textPhoto", user),
            new Report(1L, "photoPath", LocalDate.now(), "textPhoto", user)
    );

    @Test
    @DisplayName("Получить все отчеты")
    void shouldReturnCollectionOfReportWhenFindAllReportCalled() {
        when(reportRepository.findAll())
                .thenReturn(REPORT_LIST);

        assertIterableEquals(REPORT_LIST, reportService.getAllReports());
    }

    @Test
    @DisplayName("Получить отчет по его id")
    void shouldReturnReportWhenFindByIdCalled() {
        when(reportRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(report));

        assertEquals(report, reportService.findReportById(anyLong()));
    }

    @Test
    @DisplayName("Создание отчета")
    void shouldReturnReportWhenCreateReportCalled() {
        when(reportService.createReport(report)).thenReturn(report);

        assertEquals(reportService.createReport(report), report);
    }

    @Test
    @DisplayName("Редактирование отчета")
    void shouldReturnReportWhenEditReportCalled() {

        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));

       assertEquals(reportService.editReport(report), report);
    }

    @Test
    @DisplayName("Удаление отчета по его id")
    void shouldReturnTrueWhenDeleteReportByIdCalled() {

        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));

        assertTrue(reportService.deleteReportById(anyLong()));
    }
}
