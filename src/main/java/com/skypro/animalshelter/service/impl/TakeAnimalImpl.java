package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.model.ShelterUserType;
import com.skypro.animalshelter.repository.AnimalRepository;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.TakeAnimal;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.skypro.animalshelter.util.CallbackDataRequest.CAT;
import static com.skypro.animalshelter.util.CallbackDataRequest.DOG;

@Service
public class TakeAnimalImpl implements TakeAnimal {

    private final SheltersUserRepository userRepository;
    private final MessageSender messageSender;
    private final AnimalRepository animalRepository;
    private final TelegramBot telegramBot;

    public TakeAnimalImpl(SheltersUserRepository userRepository, MessageSender messageSender, AnimalRepository animalRepository, TelegramBot telegramBot) {
        this.userRepository = userRepository;
        this.messageSender = messageSender;
        this.animalRepository = animalRepository;
        this.telegramBot = telegramBot;
    }

    @Override
    public SendMessage takeAnimal(Long chatId, Boolean isCat) {

        Optional<SheltersUser> user = userRepository.findSheltersUserByChatId(chatId);

        if (user.isEmpty()) {
            SendMessage sendMessage = new SendMessage(chatId,"Пожалуйста, прежде чем взять себе питомца оставьте " +
                    "свои контактные данные в формате \"Имя Фамилия Номер телефона с кодом +7\"");
            telegramBot.execute(sendMessage);
            return sendMessage;
        }

        if (user.get().getAnimal() != null) {
            SendMessage sendMessage = new SendMessage(chatId,"Больше одного животного в нашем приюте брать нельзя");
            telegramBot.execute(sendMessage);
            return sendMessage;
        }

        Optional<Animal> animal = isCat ? animalRepository.findFirstAnimalByTypeAnimalAndInShelter(CAT.getCallbackData(), true) : animalRepository.findFirstAnimalByTypeAnimalAndInShelter(DOG.getCallbackData(), true);
        if (animal.isEmpty()) {
            return messageSender.sendMessage(chatId, "Извините, сейчас в приюте нет животных");
        }

        animal.get().setInShelter(false);
        animalRepository.save(animal.get());

        user.get().setAnimal(animal.get());
        user.get().setDataAdopt(LocalDate.now());
        user.get().setUserType(ShelterUserType.PROBATION);
        userRepository.save(user.get());

        return messageSender.sendMessage(chatId, "Поздравляем! Вы приютили себя питомца, не забывайте отправлять " +
                "ежедневные отчеты о питомце");
    }
}
