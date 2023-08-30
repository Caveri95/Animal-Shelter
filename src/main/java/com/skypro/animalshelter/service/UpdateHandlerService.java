package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.model.Update;

public interface UpdateHandlerService {

    void messageHandler(Update update);
}
