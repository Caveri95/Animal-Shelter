package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.skypro.animalshelter.service.UpdateHandlerService;
import org.springframework.stereotype.Service;

@Service
public class UpdateHandlerServiceImpl implements UpdateHandlerService {

    private final TelegramBot telegramBot;
    private final MenuServiceImpl menuService;

    public UpdateHandlerServiceImpl(TelegramBot telegramBot, MenuServiceImpl menuService) {
        this.telegramBot = telegramBot;
        this.menuService = menuService;
    }

    public void messageHandler(Update update) {
        Long chatId = update.message().chat().id();
        String userText = update.message().text();

            if ("/start".equals(userText)) {
                menuService.getStartMenuShelter(chatId);
            } else if (userText != null) {

                //Сюда сообщения остальные (например для заполнения контактной информации)

            }




    }
}
