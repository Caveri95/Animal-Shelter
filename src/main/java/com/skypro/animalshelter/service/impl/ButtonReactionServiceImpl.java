package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.ShelterInfo;
import com.skypro.animalshelter.model.ShelterType;
import com.skypro.animalshelter.model.User;
import com.skypro.animalshelter.repository.ShelterInfoRepository;
import com.skypro.animalshelter.repository.UserRepository;
import com.skypro.animalshelter.service.ButtonReactionService;
import com.skypro.animalshelter.service.MenuService;
import com.skypro.animalshelter.util.CallbackDataRequest;
import com.skypro.animalshelter.util.KeyboardUtil;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.skypro.animalshelter.util.CallbackDataRequest.*;

@Service
public class ButtonReactionServiceImpl implements ButtonReactionService {

    private final TelegramBot telegramBot;
    private final MenuService menuService;
    private final KeyboardUtil keyboardUtil;
    private final MessageSender messageSender;
    private final ShelterInfoRepository shelterInfoRepository;


    public ButtonReactionServiceImpl(TelegramBot telegramBot, MenuService menuService, KeyboardUtil keyboardUtil, MessageSender messageSender, ShelterInfoRepository shelterInfoRepository) {
        this.telegramBot = telegramBot;
        this.menuService = menuService;
        this.keyboardUtil = keyboardUtil;
        this.messageSender = messageSender;
        this.shelterInfoRepository = shelterInfoRepository;
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
                return messageSender.sendMessageWithKeyboard(chatId, "Вы выбрали приют для кошек, чем могу помочь?", keyboard);

            case DOG:

                InlineKeyboardMarkup keyboard1 = keyboardUtil.setKeyboard(    // Чтобы каждый раз по разному не называть можно в метод replyMarkup сразу вставлять или думать над классом клавиатуры
                        GENERAL_SHELTER_INFO,
                        HOW_TO_TAKE_ANIMAL,
                        REPORT_ANIMAL,
                        VOLUNTEER);
                return messageSender.sendMessageWithKeyboard(chatId, "Вы выбрали приют для собак, чем могу помочь?", keyboard1);

            case GENERAL_SHELTER_INFO:
                return menuService.getInfoAboutShelter(chatId);

            case ABOUT_SHELTER: //тут разделение на кошачий и собачий нужно по типу приюта пользователя

                return messageSender.sendMessage(chatId, SHELTER_INFO.getText());

            case CONTACT_SHELTER:
                return messageSender.sendMessage(chatId, CONTACT_SHELTER.getText());

            case SAFETY_CONTACT_FOR_CAR_PASS:
                return messageSender.sendMessage(chatId, SHELTER_CAR_PASS.getText());

            case SAFETY_IN_SHELTER_TERRITORY:
                return messageSender.sendMessage(chatId, SHELTER_SAFETY_INFO.getText());

            case GIVE_MY_CONTACT:
                return messageSender.sendMessage(chatId, "Тут какая то форма для заполнения контактов");

            case CALL_VOLUNTEER:
                return messageSender.sendMessage(chatId, "Позвать волонтера, наверно отдельный какой то чат с волонтером");

            case ROLLBACK:
                return menuService.getStartMenuShelter(chatId); //Тут возврат в меню выбора кошек/собак, надо поменять на следующий шаг

            default:
                return messageSender.sendMessage(chatId, "Позвать волонтера");

        }
    }


}
