package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.skypro.animalshelter.exception.ReportNotFoundException;
import com.skypro.animalshelter.exception.ShelterUserNotFoundException;
import com.skypro.animalshelter.model.Report;
import com.skypro.animalshelter.model.ShelterUserType;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.repository.ReportRepository;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.ReportService;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

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

        Optional<SheltersUser> user = userRepository.findSheltersUserByChatId(chatId);

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

        user.ifPresent(newReport::setSheltersUser);
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

    @Scheduled(cron = "0 00 21 * * *") //напоминания каждый день, если до 21 отчет так и не был прислан
    public void reportReminder() {

        List<Long> usersId = userRepository.findAll().stream().filter(shelterUser -> shelterUser.getDataAdopt() != null).map(SheltersUser::getId).toList();
        for (Long id : usersId) {

            Optional<Report> report = reportRepository.findBySheltersUserId(id)
                    .stream()
                    .sorted(Comparator.comparing(Report::getLocalDate))
                    .reduce((first, second) -> second);
            if (report.isPresent()) {
                if (report.get().getLocalDate().isAfter(report.get().getLocalDate().plusDays(1))) {
                    messageSender.sendMessage(report.get().getSheltersUser().getChatId(), "Напоминаю о необходимости присылать отчеты каждый день о жизни вашего питомца");
                }
            }
        }
    }

    @Scheduled(cron = "0 00 21 * * *") //напоминание в 21 если уже 2 дня не было отчетов
    public void reportReminderTwoDaysNoReport() {

        List<Long> usersId = userRepository.findAll().stream().filter(shelterUser -> shelterUser.getDataAdopt() != null).map(SheltersUser::getId).toList();
        for (Long id : usersId) {

            Optional<Report> report = reportRepository.findBySheltersUserId(id).stream().sorted(Comparator.comparing(Report::getLocalDate)).reduce((first, second) -> second);
            if (report.isPresent()) {
                if (report.get().getLocalDate().isAfter(report.get().getLocalDate().plusDays(2))) {
                    messageSender.sendMessage(report.get().getSheltersUser().getChatId(), "Вы не присылали отчет уже 2 дня, с вами свяжется наш волонтер");
                }
            }
        }
    }

    @Scheduled(cron = "0 00 12 * * *") //когда прошло 30 дней и принимается решение от волонтеров. Тут сделан выбор по рандому
    public void probationSolutionSuccess() {

        List<Long> usersId = userRepository.findAll().stream().filter(shelterUser -> shelterUser.getDataAdopt() != null).map(SheltersUser::getId).toList();
        for (Long id : usersId) {

            Optional<Report> report = reportRepository.findBySheltersUserId(id).stream().sorted(Comparator.comparing(Report::getLocalDate)).reduce((first, second) -> second);
            if (report.isPresent()) {
                if (report.get().getLocalDate().isAfter(report.get().getLocalDate().plusMonths(1))) {
                    SheltersUser user = userRepository.findById(id).orElseThrow(ShelterUserNotFoundException::new);

                    int random = (int) (Math.random() * 4);
                    switch (random) {
                        case 1:
                            user.setUserType(ShelterUserType.SUCCESSFUL_COMPLETION);
                            messageSender.sendMessage(report.get().getSheltersUser().getChatId(),
                                    "Поздравляю! Вы прошли испытательный срок и можете оставить питомца себе");;
                            break;
                        case 2:
                            user.setUserType(ShelterUserType.FAILED);
                            messageSender.sendMessage(report.get().getSheltersUser().getChatId(),
                                    "Вы не прошли испытательный срок. Наши волонтеры с Вами свяжутся");;
                            break;
                        case 3:
                            user.setUserType(ShelterUserType.PROBATION_EXTEND_14);
                            messageSender.sendMessage(report.get().getSheltersUser().getChatId(),
                                    "Волонтеры решили, что Ваш испытательный срок будет продлен на 14 дней. " +
                                            "Продолжайте ежедневно присылать отчеты о питомце");
                            break;
                        case 4:
                            user.setUserType(ShelterUserType.PROBATION_EXTEND_30);
                            messageSender.sendMessage(report.get().getSheltersUser().getChatId(),
                                    "Волонтеры решили, что Ваш испытательный срок будет продлен на 30 дней. " +
                                            "Продолжайте ежедневно присылать отчеты о питомце");;
                            break;
                    }
                }
            }
        }
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
            throw new ReportNotFoundException();
        }
    }

    @Override
    public Report findReportById(Long id) {

        return reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
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
            throw new ReportNotFoundException();
        }
    }
}
