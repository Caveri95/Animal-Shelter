package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
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
    public SendMessage getStartMenuShelter(Long chatId) {

        InlineKeyboardButton button1 = new InlineKeyboardButton("CAT");
        button1.callbackData("CAT");
        InlineKeyboardButton button2 = new InlineKeyboardButton("DOG");
        button2.callbackData("DOG");

        Keyboard keyboard = new InlineKeyboardMarkup(button1, button2);

        SendMessage sendMessage = new SendMessage(chatId, "Привет, выбери приют").replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }

    @Override
    public SendMessage getInfoAboutShelter(Long chatId) {

        InlineKeyboardButton button1 = new InlineKeyboardButton("INFO_ABOUT_SHELTER");
        button1.callbackData("INFO_ABOUT_SHELTER");
        InlineKeyboardButton button2 = new InlineKeyboardButton("CONTACT_SHELTER");
        button2.callbackData("CONTACT_SHELTER");
        InlineKeyboardButton button3 = new InlineKeyboardButton("INFO_ABOUT_CAR_PASS");
        button3.callbackData("INFO_ABOUT_CAR_PASS");
        InlineKeyboardButton button4 = new InlineKeyboardButton("SAFETY_IN_SHELTER_TERRITORY");
        button4.callbackData("SAFETY_IN_SHELTER_TERRITORY");
        InlineKeyboardButton button5 = new InlineKeyboardButton("GIVE_MY_CONTACT");
        button5.callbackData("GIVE_MY_CONTACT");
        InlineKeyboardButton button6 = new InlineKeyboardButton("CALL_VOLUNTEER");
        button6.callbackData("CALL_VOLUNTEER");

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.addRow(button1);
        keyboard.addRow(button2);
        keyboard.addRow(button3);
        keyboard.addRow(button4);
        keyboard.addRow(button5);
        keyboard.addRow(button6);

        SendMessage sendMessage = new SendMessage(chatId, "Выберите интересующую вас информацию").replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }


}
