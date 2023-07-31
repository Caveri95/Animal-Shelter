package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface MenuService {
    SendMessage getStartMenuShelter(Long chatId);

    SendMessage getInfoAboutShelter(Long chatId);
}
