package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.service.ButtonReactionService;
import com.skypro.animalshelter.service.MenuService;
import com.skypro.animalshelter.util.CallbackDataRequest;
import com.skypro.animalshelter.util.KeyboardUtil;
import org.springframework.stereotype.Service;

import static com.skypro.animalshelter.util.CallbackDataRequest.*;

@Service
public class ButtonReactionServiceImpl implements ButtonReactionService {

    private final TelegramBot telegramBot;
    private final MenuService menuService;
    private final KeyboardUtil keyboardUtil;

    public ButtonReactionServiceImpl(TelegramBot telegramBot, MenuService menuService, KeyboardUtil keyboardUtil) {
        this.telegramBot = telegramBot;
        this.menuService = menuService;
        this.keyboardUtil = keyboardUtil;
    }

    @Override
    public SendMessage buttonReaction(CallbackQuery callbackQuery) {

        Long chatId = callbackQuery.message().chat().id();
        String data = callbackQuery.data();
        CallbackDataRequest constantByRequest = CallbackDataRequest.getConstantByRequest(data); // Здесь ищем нужную константу в зависимости от пришедшего callbackQuery

        switch (constantByRequest) {

            case CAT:

                InlineKeyboardMarkup keyboard = keyboardUtil.setKeyboard(
                        GENERAL_SHELTER_INFO,
                        HOW_TO_TAKE_ANIMAL,
                        REPORT_ANIMAL,
                        VOLUNTEER);
                return sendMessageWithKeyboard(chatId, "Вы выбрали приют для кошек, чем могу помочь?", keyboard);

            case DOG:

                InlineKeyboardMarkup keyboard1 = keyboardUtil.setKeyboard(    // Чтобы каждый раз по разному не называть можно в метод replyMarkup сразу вставлять или думать над классом клавиатуры
                        GENERAL_SHELTER_INFO,
                        HOW_TO_TAKE_ANIMAL,
                        REPORT_ANIMAL,
                        VOLUNTEER);
                return sendMessageWithKeyboard(chatId, "Вы выбрали приют для собак, чем могу помочь?", keyboard1);

            case GENERAL_SHELTER_INFO:
                return menuService.getInfoAboutShelter(chatId);

            case ABOUT_SHELTER: //тут разделение на кошачий и собачий нужно
                return sendMessage(chatId, SHELTER_INFO.getText());

            case CONTACT_SHELTER:
                return sendMessage(chatId, SHELTER_CONTACT.getText());

            case SAFETY_CONTACT_FOR_CAR_PASS:
                return sendMessage(chatId, SHELTER_CAR_PASS.getText());

            case SAFETY_IN_SHELTER_TERRITORY:
                return sendMessage(chatId, SHELTER_SAFETY_INFO.getText());

            case GIVE_MY_CONTACT:
                return sendMessage(chatId, "Тут какая то форма для заполнения контактов");

            case CALL_VOLUNTEER:
                return sendMessage(chatId, "Позвать волонтера, наверно отдельный какой то чат с волонтером");

            case ROLLBACK:
                return menuService.getStartMenuShelter(chatId); //Тут возврат в меню выбора кошек/собак, надо поменять на следующий шаг

            default:
                return sendMessage(chatId, "Позвать волонтера");

        }
    }

    private SendMessage sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }

    private SendMessage sendMessageWithKeyboard(Long chatId, String message, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage(chatId, message).replyMarkup(keyboardMarkup);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }
}
