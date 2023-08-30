package com.skypro.animalshelter.controller;

import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.Report;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@Tag(name = "Отчеты о питомцах", description = "CRUD-операции и другие эндпоинты для работы с отчетами")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    @Operation(summary = "Добавить новый отчет", description = "Введите данные")
    @ApiResponse(responseCode = "200", description = "Отчет добавлен", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Report.class)))
    })
    public ResponseEntity<Report> addReport(@RequestBody Report report) {

        return ResponseEntity.ok(reportService.createReport(report));
    }

    @GetMapping
    @Operation(summary = "Получить список всех отчетов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список отчетов получен", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Report.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Ничего не найдено"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        if (reports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить отчет по его id", description = "Введите id отчета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отчет получен", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "404", description = "Животное не найдено"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<Report> getReportById(@PathVariable long id) {

        Report reportById = reportService.findReportById(id);
        if (reportById == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(reportById);
        }
    }

    @PutMapping
    @Operation(summary = "Отредактировать отчет", description = "Введите id отчета и новые данные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отчет отредактирован", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Report.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "404", description = "Отчет не найден"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<Report> editReport(@RequestBody Report report) {

        Report editReport = reportService.editReport(report);
        if (editReport == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(editReport);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отчет из базы данных", description = "Необходимо указать id отчета, который нужно удалить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отчет удален", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Animal.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")

    })
    public ResponseEntity<Void> deleteReport(@PathVariable long id) {

        if (reportService.deleteReportById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
