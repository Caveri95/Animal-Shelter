package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.ShelterInfo;

import java.util.Optional;

public interface MenuService {
    SendMessage getStartMenuShelter(Long chatId);


    SendMessage getInfoAboutShelter(Long chatId);

    SendMessage getInfoAboutTakeAnimal(Long chatId, boolean isCat);
}
