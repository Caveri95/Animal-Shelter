package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.exception.ReportNotFoundException;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.Report;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.repository.ReportRepository;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.impl.ReportServiceImpl;
import com.skypro.animalshelter.util.MessageSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.skypro.animalshelter.model.ShelterUserType.JUST_LOOKING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;
    @Mock
    private SheltersUserRepository userRepository;
    @InjectMocks
    private ReportServiceImpl reportService;
    @InjectMocks
    private MessageSender messageSender;
    @Mock
    private TelegramBot telegramBot;
    @Captor
    private ArgumentCaptor<SendMessage> captor;

    String text = "Напоминаю о необходимости присылать отчеты каждый день о жизни вашего питомца";
    Long id = 1L;

    static Animal animal = new Animal(1L, "CAT", "Британец", true);
    static SheltersUser user = new SheltersUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING);

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

        assertEquals(report, reportService.createReport(report));
    }

    @Test
    @DisplayName("Редактирование отчета")
    void shouldReturnReportWhenEditReportCalled() {

        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));

        assertEquals(report, reportService.editReport(report));
    }

    @Test
    @DisplayName("Удаление отчета по его id")
    void shouldReturnTrueWhenDeleteReportByIdCalled() {

        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));

        assertTrue(reportService.deleteReportById(anyLong()));
    }

    @Test
    @DisplayName("Ошибка при удалении отчета")
    void shouldReturnExceptionWhenDeleteAnimalByIdCalled() {
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ReportNotFoundException.class, () -> reportService.deleteReportById(anyLong()));
    }

    @Test
    @DisplayName("Ошибка при редактировании отчета")
    void shouldReturnExceptionWhenEditAnimalCalled() {
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ReportNotFoundException.class, () -> reportService.editReport(report));
    }

    @Test
    @DisplayName("Ошибка при поиске отчета по id")
    void shouldReturnExceptionWhenFindByIdCalled() {
        when(reportRepository.findById(anyLong())).thenThrow(ReportNotFoundException.class);

        assertThrows(ReportNotFoundException.class, () -> reportService.findReportById(anyLong()));
    }
    @Test
    @DisplayName("Вывод напоминания о необходимости отправить отчет")
    void shouldReturnReportReminder() {
        when(userRepository.findSheltersUserByDataAdoptIsNotNull()).thenReturn(List.of(user));
        reportService.reportReminder();

        messageSender.sendMessage(id, text);
        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
    }

    @Test
    @DisplayName("Вывод напоминания после 2 дней о необходимости отправить отчет")
    void shouldReturnReportReminderTwoDaysNoReport() {
        when(userRepository.findSheltersUserByDataAdoptIsNotNull()).thenReturn(List.of(user));

        reportService.reportReminderTwoDaysNoReport();

        messageSender.sendMessage(id, text);
        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
    }
}
