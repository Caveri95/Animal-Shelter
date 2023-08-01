package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.service.MenuService;
import com.skypro.animalshelter.util.KeyboardUtil;
import org.springframework.stereotype.Service;

import static com.skypro.animalshelter.util.CallbackDataRequest.*;

@Service
public class MenuServiceImpl implements MenuService {

    private final TelegramBot telegramBot;
    private final KeyboardUtil keyboardUtil;

    public MenuServiceImpl(TelegramBot telegramBot, KeyboardUtil keyboardUtil) {
        this.telegramBot = telegramBot;
        this.keyboardUtil = keyboardUtil;
    }

    @Override
    public SendMessage getStartMenuShelter(Long chatId) {

        InlineKeyboardMarkup keyboard = keyboardUtil.setKeyboard(CAT, DOG);

        SendMessage sendMessage = new SendMessage(chatId, "Привет, выбери приют").replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }

    @Override
    public SendMessage getInfoAboutShelter(Long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardUtil.setKeyboard(
                ABOUT_SHELTER,                                             // тут нужно выводить разную информацию в зависимости от выбранного приюта
                CONTACT_SHELTER,
                SAFETY_CONTACT_FOR_CAR_PASS,
                SAFETY_IN_SHELTER_TERRITORY,
                GIVE_MY_CONTACT,
                VOLUNTEER,
                ROLLBACK);

        SendMessage sendMessage = new SendMessage(chatId, "Выберите интересующую вас информацию").replyMarkup(inlineKeyboardMarkup);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }

}
