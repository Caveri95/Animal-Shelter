package com.skypro.animalshelter.util;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    private final TelegramBot telegramBot;

    public MessageSender(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public SendMessage sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }

    public SendMessage sendMessageWithKeyboard(Long chatId, String message, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage(chatId, message).replyMarkup(keyboardMarkup);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }
}
