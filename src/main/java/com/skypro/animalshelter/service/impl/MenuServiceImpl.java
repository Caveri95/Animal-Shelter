package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    private final TelegramBot telegramBot;

    public MenuServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void getStartMenuShelter(Long chatId) {

        InlineKeyboardButton button1 = new InlineKeyboardButton("CAT");
        button1.callbackData("CAT");
        InlineKeyboardButton button2 = new InlineKeyboardButton("DOG");
        button2.callbackData("DOG");

        Keyboard keyboard = new InlineKeyboardMarkup(button1, button2);

        SendMessage sendMessage = new SendMessage(chatId, "Привет, выбери приют").replyMarkup(keyboard);
        telegramBot.execute(sendMessage);

    }
}
