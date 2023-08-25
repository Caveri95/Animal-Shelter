package com.skypro.animalshelter.util;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.skypro.animalshelter.util.CallbackDataRequest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private MessageSender messageSender;

    @Captor
    private ArgumentCaptor<SendMessage> captor;
    @Mock
    private KeyboardUtil keyboardUtil;

    Long id = 1L;
    String text = "Тестовое сообщение пользователю";

    @Test
    @DisplayName("Вывод сообщения")
    void sendMessage() {
        messageSender.sendMessage(id, text);
        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
    }

    @Test
    @DisplayName("Вывод сообщения с клавиатурой")
    void sendMessageWithKeyboard() {

        when(keyboardUtil.setKeyboard(CAT)).thenReturn(new InlineKeyboardMarkup().addRow(new InlineKeyboardButton(CAT.getText())));
        InlineKeyboardMarkup keyboard = keyboardUtil.setKeyboard(CAT);

        messageSender.sendMessageWithKeyboard(id, text, keyboard);
        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("reply_markup"), keyboard);
    }
}