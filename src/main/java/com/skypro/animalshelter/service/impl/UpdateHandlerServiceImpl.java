package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.ReportService;
import com.skypro.animalshelter.service.UpdateHandlerService;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UpdateHandlerServiceImpl implements UpdateHandlerService {

    private final TelegramBot telegramBot;
    private final MenuServiceImpl menuService;
    private final SheltersUserRepository userRepository;
    private final MessageSender messageSender;
    private final ReportService reportService;

    private final Pattern pattern = Pattern.compile("([А-я\\d.,!?:]+)\\s+([А-я\\d.,!?:]+)\\s+(\\+7\\d{10})");


    public UpdateHandlerServiceImpl(TelegramBot telegramBot, MenuServiceImpl menuService, SheltersUserRepository userRepository, MessageSender messageSender, ReportService reportService) {
        this.telegramBot = telegramBot;
        this.menuService = menuService;
        this.userRepository = userRepository;
        this.messageSender = messageSender;
        this.reportService = reportService;
    }

    public void messageHandler(Update update) {
        Long chatId = update.message().chat().id();
        String userText = update.message().text();

        if ("/start".equals(userText)) {

            if (userRepository.findSheltersUserByChatId(chatId).isEmpty()) {
                menuService.getFirstStartMenuShelter(chatId);
            } else {
                menuService.getStartMenuShelter(chatId);
            }

        } else if (userText != null) {

            Matcher matcher = pattern.matcher(userText);
            if (matcher.find()) {

                String name = matcher.group(1);
                String surname = matcher.group(2);
                String phoneNumber = matcher.group(3);

                SheltersUser user = userRepository.findSheltersUserByChatId(chatId).isPresent() ?
                        userRepository.findSheltersUserByChatId(chatId).get() : new SheltersUser();

                user.setChatId(chatId);
                user.setName(name);
                user.setSurname(surname);
                user.setPhoneNumber(phoneNumber);
                userRepository.save(user);

                messageSender.sendMessage(chatId, "Ваши данные успешно обновлены.");
            } else {
                messageSender.sendMessage(chatId, "Неккоректный формат данных!");
            }

        } else if (update.message().photo() != null || update.message().caption() != null) {
            reportService.postReport(chatId, update);
        }


    }
}
