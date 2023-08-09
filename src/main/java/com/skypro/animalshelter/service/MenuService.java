package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface MenuService {
    SendMessage getFirstStartMenuShelter(Long chatId);


    SendMessage getStartMenuShelter(Long chatId);

    SendMessage getCatMenu(Long chatId);

    SendMessage getDogMenu(Long chatId);

    SendMessage getInfoAboutShelter(Long chatId);

    SendMessage getInfoAboutTakeAnimal(Long chatId, boolean isCat);
}
