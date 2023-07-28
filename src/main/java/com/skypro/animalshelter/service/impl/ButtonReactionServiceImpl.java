package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.service.ButtonReactionService;
import org.springframework.stereotype.Service;

@Service
public class ButtonReactionServiceImpl implements ButtonReactionService {

    private final TelegramBot telegramBot;

    public ButtonReactionServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public SendMessage buttonReaction(CallbackQuery callbackQuery) {

        Long chatId = callbackQuery.message().chat().id();
        String data = callbackQuery.data();

        switch (data) {

            case "CAT":

                InlineKeyboardButton button1 = new InlineKeyboardButton("SHELTER_INFO");
                button1.callbackData("SHELTER_INFO");
                InlineKeyboardButton button2 = new InlineKeyboardButton("HOW_TO_TAKE_ANIMAL");
                button2.callbackData("HOW_TO_TAKE_ANIMAL");
                InlineKeyboardButton button3 = new InlineKeyboardButton("REPORT_ANIMAL");
                button3.callbackData("REPORT_ANIMAL");
                InlineKeyboardButton button4 = new InlineKeyboardButton("VOLUNTEER");
                button4.callbackData("VOLUNTEER");

                Keyboard keyboard = new InlineKeyboardMarkup(button1, button2, button3, button4);

                SendMessage messageCAT = new SendMessage(chatId, "Вы выбрали приют для кошек, чем могу помочь?").replyMarkup(keyboard);
                telegramBot.execute(messageCAT);
                return messageCAT;
            case "DOG":

                SendMessage messageDOG = new SendMessage(chatId, "Вы выбрали приют для собак, чем могу помочь?");
                telegramBot.execute(messageDOG);
                return messageDOG;

            default: return new SendMessage(chatId, "Позвать волонтера");

        }



    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);

        telegramBot.execute(sendMessage);

    }
}
