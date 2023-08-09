package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.skypro.animalshelter.model.ShelterUsers;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.UpdateHandlerService;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UpdateHandlerServiceImpl implements UpdateHandlerService {

    private final TelegramBot telegramBot;
    private final MenuServiceImpl menuService;
    private final SheltersUserRepository userRepository;
    private final MessageSender messageSender;

    private final Pattern pattern = Pattern.compile("([А-я\\d.,!?:]+)\\s+([А-я\\d.,!?:]+)\\s+(\\+7\\d{10})");


    public UpdateHandlerServiceImpl(TelegramBot telegramBot, MenuServiceImpl menuService, SheltersUserRepository userRepository, MessageSender messageSender) {
        this.telegramBot = telegramBot;
        this.menuService = menuService;
        this.userRepository = userRepository;
        this.messageSender = messageSender;
    }

    public void messageHandler(Update update) {
        Long chatId = update.message().chat().id();
        String userText = update.message().text();

        if ("/start".equals(userText)) {

            if (userRepository.findSheltersUserByChatId(chatId).isEmpty()) {
                /*ShelterUsers user  = new ShelterUsers();
                user.setChatId(chatId);
                user.setName(update.message().chat().firstName());
                userRepository.save(user);*/
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

                if (userRepository.findSheltersUserByChatId(chatId).isPresent()) {
                    ShelterUsers user = userRepository.findSheltersUserByChatId(chatId).get();
                    user.setChatId(chatId);
                    user.setName(name);
                    user.setSurname(surname);
                    user.setPhoneNumber(phoneNumber);
                    userRepository.save(user);
                } else {
                    ShelterUsers user = new ShelterUsers();
                    user.setChatId(chatId);
                    user.setName(name);
                    user.setSurname(surname);
                    user.setPhoneNumber(phoneNumber);
                    userRepository.save(user);
                }

                messageSender.sendMessage(chatId, "Ваши данные успешно обновлены");
            } else {
                messageSender.sendMessage(chatId, "Неккоректный формат сообщения!");
            }

        }


    }
}
