package com.skypro.animalshelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.Report;
import com.skypro.animalshelter.model.ShelterUser;
import com.skypro.animalshelter.service.AnimalService;
import com.skypro.animalshelter.service.ReportService;
import com.skypro.animalshelter.service.ShelterUserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReportService reportService;



    @Autowired
    private ObjectMapper objectMapper;

    Animal animal = new Animal(1L, "CAT", "Британец", true);
    ShelterUser user = new ShelterUser(6L, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal);
    Report report = new Report(1L, "photoPath1", LocalDate.now(), "textUnderFoto1", user);
    List<Report> listReports = new ArrayList<>(List.of(
            new Report(1, "photoPath1", LocalDate.now(), "textUnderFoto1", user),
            new Report(2, "photoPath2", LocalDate.now(), "textUnderFoto2", user),
            new Report(3, "photoPath3", LocalDate.now(), "textUnderFoto3", user)
    ));

    @Test
    @DisplayName("Добавление отчета")
    void shouldReturnReportWhenAddUserCalled() throws Exception {

        when(reportService.createReport(report)).thenReturn(report);

        mvc.perform(MockMvcRequestBuilders.post("/report")
                        .content(objectMapper.writeValueAsString(report))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.photo").value("photoPath1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localDate").value(LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reportTextUnderPhoto").value("textUnderFoto1"))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.shelterUser").value(user))    Не понятно почему не видит юзера
                .andExpect(status().isOk());

        verify(reportService, only()).createReport(report);

    }

    @Test
    @DisplayName("Редактирование отчета")
    void shouldReturnReportWhenEditUserCalled() throws Exception {

        when(reportService.editReport(report)).thenReturn(report);

        mvc.perform(MockMvcRequestBuilders.put("/report")
                        .content(objectMapper.writeValueAsString(report))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.photo").value("photoPath1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localDate").value(LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reportTextUnderPhoto").value("textUnderFoto1"))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.shelterUser").value(user))
                .andExpect(status().isOk());

        verify(reportService, only()).editReport(report);

    }

    @Test
    @DisplayName("Вывод всех отчетов")
    void shouldReturnReportListWhenGetAllUserCalled() throws Exception {
        when(reportService.getAllReports()).thenReturn(listReports);
        mvc.perform(MockMvcRequestBuilders.get("/report")
                        .content(objectMapper.writeValueAsString(listReports))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Вывод отчета по его id")
    void shouldReturnReportWhenFindByIdCalled() throws Exception {
        when(reportService.findReportById(anyLong())).thenReturn(report);
        mvc.perform(MockMvcRequestBuilders.get("/report/{id}", report.getId()))
                .andExpect(status().isOk());

        verify(reportService, only()).findReportById(report.getId());
    }




    @Test
    @DisplayName("Удаление отчета без ошибок")
    void shouldReturnOkWhenDeleteReportByIdCalled() throws Exception {
        when(reportService.deleteReportById(report.getId())).thenReturn(true);
        mvc.perform(delete("/report/{id}", report.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление отчета с ошибкой")
    void shouldReturnNotFoundWhenDeleteReportByIdCalled() throws Exception {
        when(reportService.deleteReportById(report.getId())).thenReturn(false);
        mvc.perform(delete("/report/{id}", anyLong()))
                .andExpect(status().isNotFound());
    }
}

