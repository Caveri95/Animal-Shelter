package com.skypro.animalshelter.util;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import liquibase.pro.packaged.D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.skypro.animalshelter.util.CallbackDataRequest.CAT;
import static com.skypro.animalshelter.util.CallbackDataRequest.DOG;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MessageSenderTest {
    @Mock
    private MessageSender messageSender;

    @Mock
    private KeyboardUtil keyboardUtil;


    Long id = 1L;
    String text = "Тестовое сообщение пользователю";


    @Test
    @DisplayName("Вывод сообщения")
    void sendMessage() {


        SendMessage sendMessage = new SendMessage(id, text);

        when(messageSender.sendMessage(anyLong(), anyString())).thenReturn(sendMessage);

        assertEquals(sendMessage, messageSender.sendMessage(id, text));
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