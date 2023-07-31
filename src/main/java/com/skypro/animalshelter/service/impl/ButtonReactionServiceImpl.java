package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.Shelter;
import com.skypro.animalshelter.service.ButtonReactionService;
import com.skypro.animalshelter.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class ButtonReactionServiceImpl implements ButtonReactionService {

    private final TelegramBot telegramBot;
    private final MenuService menuService;

    public ButtonReactionServiceImpl(TelegramBot telegramBot, MenuService menuService) {
        this.telegramBot = telegramBot;
        this.menuService = menuService;
    }

    @Override
    public SendMessage buttonReaction(CallbackQuery callbackQuery) {

        Long chatId = callbackQuery.message().chat().id();
        String data = callbackQuery.data();

        switch (data) { // Названия в switch через Enum

            case "CAT":

                InlineKeyboardButton button1 = new InlineKeyboardButton("SHELTER_INFO");
                button1.callbackData("SHELTER_INFO");
                InlineKeyboardButton button2 = new InlineKeyboardButton("HOW_TO_TAKE_ANIMAL");
                button2.callbackData("HOW_TO_TAKE_ANIMAL");
                InlineKeyboardButton button3 = new InlineKeyboardButton("REPORT_ANIMAL");
                button3.callbackData("REPORT_ANIMAL");
                InlineKeyboardButton button4 = new InlineKeyboardButton("VOLUNTEER");
                button4.callbackData("VOLUNTEER");

                InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
                keyboard.addRow(button1);
                keyboard.addRow(button2);
                keyboard.addRow(button3);
                keyboard.addRow(button4);

                SendMessage messageCAT = new SendMessage(chatId, "Вы выбрали приют для кошек, чем могу помочь?").replyMarkup(keyboard);
                telegramBot.execute(messageCAT);
                return messageCAT;

            case "DOG":

                InlineKeyboardButton button5 = new InlineKeyboardButton("SHELTER_INFO");
                button5.callbackData("SHELTER_INFO");
                InlineKeyboardButton button6 = new InlineKeyboardButton("HOW_TO_TAKE_ANIMAL");
                button6.callbackData("HOW_TO_TAKE_ANIMAL");
                InlineKeyboardButton button7 = new InlineKeyboardButton("REPORT_ANIMAL");
                button7.callbackData("REPORT_ANIMAL");
                InlineKeyboardButton button8 = new InlineKeyboardButton("VOLUNTEER");
                button8.callbackData("VOLUNTEER");

                InlineKeyboardMarkup keyboard1 = new InlineKeyboardMarkup();
                keyboard1.addRow(button5);
                keyboard1.addRow(button6);
                keyboard1.addRow(button7);
                keyboard1.addRow(button8);
                SendMessage messageDOG = new SendMessage(chatId, "Вы выбрали приют для собак, чем могу помочь?").replyMarkup(keyboard1);
                telegramBot.execute(messageDOG);
                return messageDOG;

            case "SHELTER_INFO":
                return menuService.getInfoAboutShelter(chatId);

            case "INFO_ABOUT_SHELTER":
                return sendMessage(chatId, Shelter.SHELTER_INFO.getDescription());
            case "CONTACT_SHELTER":
                return sendMessage(chatId, Shelter.SHELTER_CONTACT.getDescription());
            case "INFO_ABOUT_CAR_PASS":
                return sendMessage(chatId, Shelter.SHELTER_CAR_PASS.getDescription());
            case "SAFETY_IN_SHELTER_TERRITORY":
                return sendMessage(chatId, Shelter.SHELTER_SAFETY_INFO.getDescription());

            default:
                return sendMessage(chatId, "Позвать волонтера");

        }

    }

    private SendMessage sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);

        telegramBot.execute(sendMessage);
        return sendMessage;

    }
}
