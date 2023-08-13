package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.skypro.animalshelter.model.Report;
import com.skypro.animalshelter.model.ShelterUser;
import com.skypro.animalshelter.repository.ReportRepository;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.ReportService;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {
    private final TelegramBot telegramBot;
    private final SheltersUserRepository userRepository;
    private final ReportRepository reportRepository;
    private final MessageSender messageSender;

    public ReportServiceImpl(TelegramBot telegramBot, SheltersUserRepository userRepository, ReportRepository reportRepository, MessageSender messageSender) {
        this.telegramBot = telegramBot;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.messageSender = messageSender;
    }

    @Override
    public SendMessage postReport(Long chatId, Update update) {

        Optional<ShelterUser> user = userRepository.findSheltersUserByChatId(chatId);

        if (user.isPresent()) {
            Optional<Report> reports = reportRepository.findByLocalDateEquals(LocalDate.now());
            if (reports.isPresent()) {
                if (!Objects.isNull(reports.get().getPhoto()) && !Objects.isNull(reports.get().getReportTextUnderPhoto())) {
                    return messageSender.sendMessage(chatId, "Вы уже отправляли сегодня отчет по питомцу, наши волонтеры" +
                            "посмотрят Ваш отчет");
                }
            }
        }

        Report newReport = new Report();
        String caption = update.message().caption();

        newReport.setShelterUser(user.get());
        newReport.setReportTextUnderPhoto(caption);
        newReport.setLocalDate(LocalDate.now());

        PhotoSize photoSize = update.message().photo()[update.message().photo().length - 1];
        GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
        if (getFileResponse.isOk()) {
            try {
                String extension = StringUtils.getFilenameExtension(getFileResponse.file().filePath());
                byte[] image = telegramBot.getFileContent(getFileResponse.file());
                Path write = Files.write(Paths.get(UUID.randomUUID() + "." + extension), image);
                newReport.setPhoto(write.toString());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (Objects.isNull(newReport.getPhoto()) || Objects.isNull(newReport.getReportTextUnderPhoto())) {
            return messageSender.sendMessage(chatId, "Нужно ФОТО и ОПИСАНИЕ под ним Дорогой усыновитель, мы заметили, что ты заполняешь " +
                    "отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. " +
                    "В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного");
        }
        reportRepository.save(newReport);

        return messageSender.sendMessage(chatId, "Отчет добавлен, не забывайте отправлять отчеты о вашем питомце ежедневно");


    }

    @Override
    public Boolean checkIsFullReportPostToday() {

        Optional<Report> reports = reportRepository.findByLocalDateEquals(LocalDate.now());
        if (reports.isPresent()) {
            if (!reports.get().getPhoto().isEmpty() && !reports.get().getReportTextUnderPhoto().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Report createReport(Report report) {
        reportRepository.save(report);
        return report;
    }

    @Override
    public Report editReport(Report report) {
        if (reportRepository.findById(report.getId()).isPresent()) {
            reportRepository.save(report);
            return report;
        } else {
            return null;
        }
    }
    @Override
    public Report findReportById(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        return report.orElse(null);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public boolean deleteReportById(Long id) {
        if (reportRepository.findById(id).isPresent()) {
            reportRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
