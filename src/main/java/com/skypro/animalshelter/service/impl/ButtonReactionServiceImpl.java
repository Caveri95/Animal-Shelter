package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.Shelter;
import com.skypro.animalshelter.service.ButtonReactionService;
import com.skypro.animalshelter.service.MenuService;
import com.skypro.animalshelter.util.CallbackDataRequest;
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

        CallbackDataRequest constantByRequest = CallbackDataRequest.getConstantByRequest(data); // Здесь ищем нужную константу в зависимости от пришедшего callbackQuery

        switch (constantByRequest) {

            case CAT:

                InlineKeyboardButton button1 = new InlineKeyboardButton(CallbackDataRequest.GENERAL_SHELTER_INFO.getText());
                button1.callbackData(CallbackDataRequest.GENERAL_SHELTER_INFO.getCallbackData());
                InlineKeyboardButton button2 = new InlineKeyboardButton(CallbackDataRequest.HOW_TO_TAKE_ANIMAL.getText());
                button2.callbackData(CallbackDataRequest.HOW_TO_TAKE_ANIMAL.getCallbackData());
                InlineKeyboardButton button3 = new InlineKeyboardButton(CallbackDataRequest.REPORT_ANIMAL.getText());
                button3.callbackData(CallbackDataRequest.REPORT_ANIMAL.getCallbackData());
                InlineKeyboardButton button4 = new InlineKeyboardButton(CallbackDataRequest.VOLUNTEER.getText());
                button4.callbackData(CallbackDataRequest.VOLUNTEER.getCallbackData());

                InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
                keyboard.addRow(button1);
                keyboard.addRow(button2);
                keyboard.addRow(button3);
                keyboard.addRow(button4);

                SendMessage messageCAT = new SendMessage(chatId, "Вы выбрали приют для кошек, чем могу помочь?").replyMarkup(keyboard);
                telegramBot.execute(messageCAT);
                return messageCAT;

            case DOG:

                InlineKeyboardButton button5 = new InlineKeyboardButton(CallbackDataRequest.GENERAL_SHELTER_INFO.getText());
                button5.callbackData(CallbackDataRequest.GENERAL_SHELTER_INFO.getCallbackData());
                InlineKeyboardButton button6 = new InlineKeyboardButton(CallbackDataRequest.HOW_TO_TAKE_ANIMAL.getText());
                button6.callbackData(CallbackDataRequest.HOW_TO_TAKE_ANIMAL.getCallbackData());
                InlineKeyboardButton button7 = new InlineKeyboardButton(CallbackDataRequest.REPORT_ANIMAL.getText());
                button7.callbackData(CallbackDataRequest.REPORT_ANIMAL.getCallbackData());
                InlineKeyboardButton button8 = new InlineKeyboardButton(CallbackDataRequest.VOLUNTEER.getText());
                button8.callbackData(CallbackDataRequest.VOLUNTEER.getCallbackData());

                InlineKeyboardMarkup keyboard1 = new InlineKeyboardMarkup();
                keyboard1.addRow(button5);
                keyboard1.addRow(button6);
                keyboard1.addRow(button7);
                keyboard1.addRow(button8);
                SendMessage messageDOG = new SendMessage(chatId, "Вы выбрали приют для собак, чем могу помочь?").replyMarkup(keyboard1);
                telegramBot.execute(messageDOG);
                return messageDOG;

            case GENERAL_SHELTER_INFO:
                return menuService.getInfoAboutShelter(chatId);

            case ABOUT_SHELTER:
                return sendMessage(chatId, Shelter.SHELTER_INFO.getDescription()); // В этих 4-х case инфа о приюте берется из отдельного enum про приют(можно из CallbackDataRequest)
            case CONTACT_SHELTER:
                return sendMessage(chatId, Shelter.SHELTER_CONTACT.getDescription());
            case SAFETY_CONTACT_FOR_CAR_PASS:
                return sendMessage(chatId, Shelter.SHELTER_CAR_PASS.getDescription());
            case SAFETY_IN_SHELTER_TERRITORY:
                return sendMessage(chatId, Shelter.SHELTER_SAFETY_INFO.getDescription());
            case GIVE_MY_CONTACT:
                return sendMessage(chatId, "Тут какая то форма для заполнения контактов");
            case CALL_VOLUNTEER:
                return sendMessage(chatId, "Позвать волонтера, наверно отдельный какой то чат с волонтером");

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
