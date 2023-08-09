package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface TakeAnimal {

    SendMessage takeAnimal(Long chatId, Boolean isCat);
}
