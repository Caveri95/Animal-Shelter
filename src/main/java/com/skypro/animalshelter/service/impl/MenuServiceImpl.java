package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.service.MenuService;
import com.skypro.animalshelter.util.CallbackDataRequest;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    private final TelegramBot telegramBot;

    public MenuServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public SendMessage getStartMenuShelter(Long chatId) {

        InlineKeyboardButton button1 = new InlineKeyboardButton(CallbackDataRequest.CAT.getText());
        button1.callbackData(CallbackDataRequest.CAT.getCallbackData());
        InlineKeyboardButton button2 = new InlineKeyboardButton(CallbackDataRequest.DOG.getText());
        button2.callbackData(CallbackDataRequest.DOG.getCallbackData());

        Keyboard keyboard = new InlineKeyboardMarkup(button1, button2);

        SendMessage sendMessage = new SendMessage(chatId, "Привет, выбери приют").replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }

    @Override
    public SendMessage getInfoAboutShelter(Long chatId) {

        InlineKeyboardButton button1 = new InlineKeyboardButton(CallbackDataRequest.ABOUT_SHELTER.getText());
        button1.callbackData(CallbackDataRequest.ABOUT_SHELTER.getCallbackData());
        InlineKeyboardButton button2 = new InlineKeyboardButton(CallbackDataRequest.CONTACT_SHELTER.getText());
        button2.callbackData(CallbackDataRequest.CONTACT_SHELTER.getCallbackData());
        InlineKeyboardButton button3 = new InlineKeyboardButton(CallbackDataRequest.SAFETY_CONTACT_FOR_CAR_PASS.getText());
        button3.callbackData(CallbackDataRequest.SAFETY_CONTACT_FOR_CAR_PASS.getCallbackData());
        InlineKeyboardButton button4 = new InlineKeyboardButton(CallbackDataRequest.SAFETY_IN_SHELTER_TERRITORY.getText());
        button4.callbackData(CallbackDataRequest.SAFETY_IN_SHELTER_TERRITORY.getCallbackData());
        InlineKeyboardButton button5 = new InlineKeyboardButton(CallbackDataRequest.GIVE_MY_CONTACT.getText());
        button5.callbackData(CallbackDataRequest.GIVE_MY_CONTACT.getCallbackData());
        InlineKeyboardButton button6 = new InlineKeyboardButton(CallbackDataRequest.VOLUNTEER.getText());
        button6.callbackData(CallbackDataRequest.VOLUNTEER.getCallbackData());

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
