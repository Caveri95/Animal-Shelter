package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
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
                SendMessage messageCAT = new SendMessage(chatId, "Вы выбрали приют для кошек");
                telegramBot.execute(messageCAT);
                return messageCAT;
            case "DOG":
                SendMessage messageDOG = new SendMessage(chatId, "Вы выбрали приют для собак");
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
