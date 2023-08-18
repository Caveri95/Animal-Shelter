package com.skypro.animalshelter.util;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

    @Mock
    private MessageSender messageSender;

    @Mock
    private KeyboardUtil keyboardUtil;
    TelegramBot telegramBot = mock(TelegramBot.class);
    SendResponse sendResponse = mock(SendResponse.class);


    Long id = 1L;
    String text = "Тестовое сообщение пользователю";



    @Test
    @DisplayName("Вывод сообщения")
    void sendMessage() {

    }

    @Test
    @DisplayName("Вывод сообщения с клавиатурой")
    void sendMessageWithKeyboard() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        SendMessage sendMessage = new SendMessage(id, text).replyMarkup(inlineKeyboardMarkup);

        when(messageSender.sendMessageWithKeyboard(id, text, inlineKeyboardMarkup)).thenReturn(sendMessage);

        assertEquals(messageSender.sendMessageWithKeyboard(id, text, inlineKeyboardMarkup), sendMessage);

    }
}