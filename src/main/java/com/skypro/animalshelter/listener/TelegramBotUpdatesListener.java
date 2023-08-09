package com.skypro.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.skypro.animalshelter.service.ButtonReactionService;
import com.skypro.animalshelter.service.UpdateHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final UpdateHandlerService updateHandler;
    private final ButtonReactionService buttonReactionService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, UpdateHandlerService updateHandler, ButtonReactionService buttonReactionService) {
        this.telegramBot = telegramBot;
        this.updateHandler = updateHandler;
        this.buttonReactionService = buttonReactionService;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Handles update: {}", update);

                if (update.callbackQuery() != null) {
                    buttonReactionService.buttonReaction(update.callbackQuery());

                } else if (update.message().text() != null) {
                    updateHandler.messageHandler(update);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
