package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.service.impl.MenuServiceImpl;
import com.skypro.animalshelter.util.KeyboardUtil;
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

    @InjectMocks
    private MenuServiceImpl menuService;

    @Mock
    private KeyboardUtil keyboardUtil;

    Long id = 402L;

    @Test
    @DisplayName("Вывод приветственного меню")
    void shouldReturnStartMessageMenuWhenGetStartMenuCalled() {

        String text = "Рады видеть Вас снова! Выберите приют";
        menuService.getStartMenuShelter(id);

        InlineKeyboardMarkup keyboardMarkup = keyboardUtil.setKeyboard(CAT, DOG);
        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("keyboardMarkup"), keyboardMarkup);
    }

    @Test
    @DisplayName("Вывод первого приветственного меню")
    void shouldReturnFirstStartMessageMenuWhenGetFirstStartMenuCalled() {

        String text = "Добрый день, мы всегда рады новым посетителям приюта!" +
                " Вы находитесь в меню выбора приюта для кошек или " +
                "собак, пожалуйста, выберите приют, о котором хотите узнать";
        menuService.getFirstStartMenuShelter(id);

        InlineKeyboardMarkup keyboardMarkup = keyboardUtil.setKeyboard(CAT, DOG);
        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("keyboardMarkup"), keyboardMarkup);
    }


    @Test
    @DisplayName("Вывод меню для кошки")
    void shouldReturnCatMenuWhenGetCatMenuCalled() {

        String text = "Вы выбрали приют для кошек, чем могу помочь?";
        menuService.getCatMenu(id);

        InlineKeyboardMarkup keyboardMarkup = keyboardUtil.setKeyboard(GENERAL_SHELTER_INFO,
                HOW_TO_TAKE_ANIMAL,
                REPORT_ANIMAL,
                TAKE_CAT,
                VOLUNTEER);

        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("keyboardMarkup"), keyboardMarkup);
    }

    @Test
    @DisplayName("Вывод меню для собаки")
    void shouldReturnDogMenuWhenGetDogMenuCalled() {

        String text = "Вы выбрали приют для собак, чем могу помочь?";
        menuService.getDogMenu(id);

        InlineKeyboardMarkup keyboardMarkup = keyboardUtil.setKeyboard(GENERAL_SHELTER_INFO,
                HOW_TO_TAKE_ANIMAL,
                REPORT_ANIMAL,
                TAKE_DOG,
                VOLUNTEER);

        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("keyboardMarkup"), keyboardMarkup);
    }

    @Test
    @DisplayName("Вывод меню с общей информацией")
    void shouldReturnInfoMenuWhenGetInfoMenuCalled() {

        String text = "Выберите интересующую вас информацию";
        menuService.getInfoAboutShelter(id);

        InlineKeyboardMarkup keyboardMarkup = keyboardUtil.setKeyboard(
                ABOUT_SHELTER,
                CONTACT_SHELTER,
                SAFETY_CONTACT_FOR_CAR_PASS,
                SAFETY_IN_SHELTER_TERRITORY,
                GIVE_MY_CONTACT,
                VOLUNTEER,
                ROLLBACK);

        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("keyboardMarkup"), keyboardMarkup);
    }


    @Test
    @DisplayName("Вывод меню с инструкцией как забрать собаку из приюта")
    void shouldReturnInfoTakeDogWhenGetTakeDogCalled() {

        String text = "Постараюсь дать Вам максимально полную информацию " +
                "о том как разобраться с бюрократическими (оформление договора) и бытовыми (как подготовиться к жизни с животным) " +
                "вопросами)";
        menuService.getInfoAboutTakeAnimal(id, false);

        InlineKeyboardMarkup keyboardMarkup = keyboardUtil.setKeyboard(
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

        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("keyboardMarkup"), keyboardMarkup);
    }

    @Test
    @DisplayName("Вывод меню с инструкцией как забрать кошку из приюта")
    void shouldReturnInfoTakeCatWhenGetTakeCatCalled() {

        String text = "Постараюсь дать Вам максимально полную информацию " +
                "о том как разобраться с бюрократическими (оформление договора) и бытовыми (как подготовиться к жизни с животным) " +
                "вопросами)";
        menuService.getInfoAboutTakeAnimal(id, true);

        InlineKeyboardMarkup keyboardMarkup = keyboardUtil.setKeyboard(
                SHELTER_RULES_BEFORE_MEETING_ANIMAL,
                DOCUMENTS_TO_TAKE_ANIMAL,
                TRANSPORTATION_ADVICE,
                HOUSE_RULES_FOR_SMALL_ANIMAL,
                HOUSE_RULES_FOR_ADULT_ANIMAL,
                HOUSE_RULES_FOR_ANIMAL_WITH_DISABILITY,
                GIVE_MY_CONTACT);

        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
        assertEquals(sendMessage.getParameters().get("keyboardMarkup"), keyboardMarkup);
    }











}
