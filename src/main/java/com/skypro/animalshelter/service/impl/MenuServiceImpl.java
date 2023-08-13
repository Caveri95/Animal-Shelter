package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.service.MenuService;
import com.skypro.animalshelter.util.KeyboardUtil;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;

import static com.skypro.animalshelter.util.CallbackDataRequest.*;

@Service
public class MenuServiceImpl implements MenuService {

    private final TelegramBot telegramBot;
    private final KeyboardUtil keyboardUtil;
    private final MessageSender messageSender;

    public MenuServiceImpl(TelegramBot telegramBot, KeyboardUtil keyboardUtil, MessageSender messageSender) {
        this.telegramBot = telegramBot;
        this.keyboardUtil = keyboardUtil;
        this.messageSender = messageSender;
    }

    @Override
    public SendMessage getFirstStartMenuShelter(Long chatId) {

        InlineKeyboardMarkup keyboard = keyboardUtil.setKeyboard(CAT, DOG);

        SendMessage sendMessage = new SendMessage(chatId, "Привет, (тут мини приветствие от приюта для первый раз зашедшего)" +
                " вы находитесь в меню выбора приюта для кошек или " +
                "собак, пожалуйста, выбери приют, о котором хотите узнать").replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }

    @Override
    public SendMessage getStartMenuShelter(Long chatId) {

        InlineKeyboardMarkup keyboard = keyboardUtil.setKeyboard(CAT, DOG);

        SendMessage sendMessage = new SendMessage(chatId, "Рады видеть Вас снова! Выберите приют").replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
        return sendMessage;
    }

    @Override
    public SendMessage getCatMenu(Long chatId) {
        InlineKeyboardMarkup keyboard = keyboardUtil.setKeyboard(
                GENERAL_SHELTER_INFO,
                HOW_TO_TAKE_ANIMAL,
                REPORT_ANIMAL,
                TAKE_CAT,
                VOLUNTEER);
        return messageSender.sendMessageWithKeyboard(chatId, "Вы выбрали приют для кошек, чем могу помочь?", keyboard);
    }

    @Override
    public SendMessage getDogMenu(Long chatId) {

        InlineKeyboardMarkup keyboard1 = keyboardUtil.setKeyboard(
                GENERAL_SHELTER_INFO,
                HOW_TO_TAKE_ANIMAL,
                REPORT_ANIMAL,
                TAKE_DOG,
                VOLUNTEER);
        return messageSender.sendMessageWithKeyboard(chatId, "Вы выбрали приют для собак, чем могу помочь?", keyboard1);
    }

    @Override
    public SendMessage getInfoAboutShelter(Long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardUtil.setKeyboard(
                ABOUT_SHELTER,
                CONTACT_SHELTER,
                SAFETY_CONTACT_FOR_CAR_PASS,
                SAFETY_IN_SHELTER_TERRITORY,
                GIVE_MY_CONTACT,
                VOLUNTEER,
                ROLLBACK);

        return messageSender.sendMessageWithKeyboard(chatId, "Выберите интересующую вас информацию", inlineKeyboardMarkup);
    }

    @Override
    public SendMessage getInfoAboutTakeAnimal(Long chatId, boolean isCat) {
        if (!isCat) {

            InlineKeyboardMarkup inlineKeyboardMarkup = keyboardUtil.setKeyboard(
                    SHELTER_RULES_BEFORE_MEETING_ANIMAL,
                    DOCUMENTS_TO_TAKE_ANIMAL,
                    TRANSPORTATION_ADVICE,
                    HOUSE_RULES_FOR_SMALL_ANIMAL,
                    HOUSE_RULES_FOR_ADULT_ANIMAL,
                    HOUSE_RULES_FOR_ANIMAL_WITH_DISABILITY,
                    CYNOLOGIST_ADVICE,
                    CYNOLOGISTS,
                    REFUSE_REASONS,
                    GIVE_MY_CONTACT);

            return messageSender.sendMessageWithKeyboard(chatId, "Постараюсь дать Вам максимально полную информацию " +
                    "о том как разобраться с бюрократическими (оформление договора) и бытовыми (как подготовиться к жизни с животным) " +
                    "вопросами)", inlineKeyboardMarkup);
        } else {
            InlineKeyboardMarkup inlineKeyboardMarkup = keyboardUtil.setKeyboard(
                    SHELTER_RULES_BEFORE_MEETING_ANIMAL,
                    DOCUMENTS_TO_TAKE_ANIMAL,
                    TRANSPORTATION_ADVICE,
                    HOUSE_RULES_FOR_SMALL_ANIMAL,
                    HOUSE_RULES_FOR_ADULT_ANIMAL,
                    HOUSE_RULES_FOR_ANIMAL_WITH_DISABILITY,
                    GIVE_MY_CONTACT);

            return messageSender.sendMessageWithKeyboard(chatId, "Постараюсь дать Вам максимально полную информацию " +
                    "о том как разобраться с бюрократическими (оформление договора) и бытовыми (как подготовиться к жизни с животным) " +
                    "вопросами)", inlineKeyboardMarkup);
        }
    }

}
