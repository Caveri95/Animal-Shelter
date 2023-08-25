package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.service.impl.MenuServiceImpl;
import com.skypro.animalshelter.util.KeyboardUtil;
import com.skypro.animalshelter.util.MessageSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.skypro.animalshelter.util.CallbackDataRequest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private TelegramBot telegramBot;


    @Captor
    private ArgumentCaptor<SendMessage> captor;

    private final MenuServiceImpl menuService;


    @Mock
    private KeyboardUtil keyboardUtil;
    @Mock
    private MessageSender messageSender;




    Long id = 402L;
    String text = "Вы выбрали приют для кошек, чем могу помочь?";

    public MenuServiceTest(MenuServiceImpl menuService) {
        this.menuService = menuService;
    }

    /*@Test
    @DisplayName("Вывод приветственного меню")
    void sendMessage() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        SendMessage sendMessageStartMenu = new SendMessage(id, "Рады видеть Вас снова! Выберите приют").replyMarkup(inlineKeyboardMarkup);

        when(menuService.getStartMenuShelter(id)).thenReturn(sendMessageStartMenu);

        assertEquals(sendMessageStartMenu, menuService.getStartMenuShelter(id));

    }*/


    @Test
    void getCatMenu() {

        SendMessage catMenu = menuService.getCatMenu(id);
        when(keyboardUtil.setKeyboard(CAT)).thenReturn(new InlineKeyboardMarkup());
        InlineKeyboardMarkup keyboardMarkup = keyboardUtil.setKeyboard(CAT);
        SendMessage message = new SendMessage(id, text).replyMarkup(keyboardMarkup);

        assertEquals(message, catMenu);

        //verify(telegramBot, times(1)).execute(captor.capture());



        /*var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("keyboardMarkup"), keyboardMarkup);*/
    }

    /*@Test
    void getCatMenu1() {

        menuService.getCatMenu(id);

        InlineKeyboardMarkup keyboardMarkup = keyboardUtil.setKeyboard(GENERAL_SHELTER_INFO,
                HOW_TO_TAKE_ANIMAL,
                REPORT_ANIMAL,
                TAKE_CAT,
                VOLUNTEER);
        //messageSender.sendMessageWithKeyboard(id, text, keyboardMarkup);

        verify(keyboardUtil, times(1)).setKeyboard(GENERAL_SHELTER_INFO,
                HOW_TO_TAKE_ANIMAL,
                REPORT_ANIMAL,
                TAKE_CAT,
                VOLUNTEER);

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("keyboardMarkup"), keyboardMarkup);
    }*/
}
