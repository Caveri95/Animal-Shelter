package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.service.UpdateHandlerService;
import org.springframework.stereotype.Service;

@Service
public class UpdateHandlerServiceImpl implements UpdateHandlerService {

    private final TelegramBot telegramBot;

    public UpdateHandlerServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void messageHandler(Update update) {
        Long chatId = update.message().chat().id();
        String userText = update.message().text();


        if (update.message().text() != null) {

            if ("/start".equals(userText)) {

                InlineKeyboardButton button1 = new InlineKeyboardButton("CAT");
                button1.callbackData("CAT");
                InlineKeyboardButton button2 = new InlineKeyboardButton("DOG");
                button2.callbackData("DOG");

                Keyboard keyboard = new InlineKeyboardMarkup(button1, button2);

                SendMessage sendMessage = new SendMessage(chatId, "Привет, выбери приют").replyMarkup(keyboard);
                telegramBot.execute(sendMessage);
            }
        }
    }
}
