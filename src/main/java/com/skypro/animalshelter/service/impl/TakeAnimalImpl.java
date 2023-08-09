package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.Animals;
import com.skypro.animalshelter.model.ShelterUsers;
import com.skypro.animalshelter.repository.AnimalRepository;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.TakeAnimal;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.skypro.animalshelter.util.CallbackDataRequest.CAT;
import static com.skypro.animalshelter.util.CallbackDataRequest.DOG;

@Service
public class TakeAnimalImpl implements TakeAnimal {

    private final SheltersUserRepository userRepository;
    private final MessageSender messageSender;
    private final AnimalRepository animalRepository;

    public TakeAnimalImpl(SheltersUserRepository userRepository, MessageSender messageSender, AnimalRepository animalRepository) {
        this.userRepository = userRepository;
        this.messageSender = messageSender;
        this.animalRepository = animalRepository;
    }

    @Override
    public SendMessage takeAnimal(Long chatId, Boolean isCat) {

        if (userRepository.findSheltersUserByChatId(chatId).isEmpty()) {
            return messageSender.sendMessage(chatId, "Пожалуйста, прежде чем взять себе питомца оставьте " +
                    "свои контактные данные в формате \"Имя Фамилия Номер телефона с кодом +7\"");
        }

        if (userRepository.findSheltersUserByChatId(chatId).isPresent()) {
            if (userRepository.findSheltersUserByChatId(chatId).get().getAnimals() != null) {
                return messageSender.sendMessage(chatId, "Больше одного животного в нашем приюте брать нельзя");
            }

            ShelterUsers user = userRepository.findSheltersUserByChatId(chatId).get();
            if (isCat) {
                Optional<Animals> cat = animalRepository.findAnimalByTypeAnimal(CAT.getCallbackData()).stream().filter(Animals::getInShelter).findAny();
                if (cat.isEmpty()) {
                    return messageSender.sendMessage(chatId, "Извините, сейчас в приюте нет котов");
                }
                user.setAnimals(cat.get());
                cat.get().setInShelter(false);
                animalRepository.save(cat.get());


            } else {
                Optional<Animals> dog = animalRepository.findAnimalByTypeAnimal(DOG.getCallbackData()).stream().filter(Animals::getInShelter).findAny();
                if (dog.isEmpty()) {
                    return messageSender.sendMessage(chatId, "Извините, сейчас в приюте нет собак");
                }
                user.setAnimals(dog.get());
                dog.get().setInShelter(false);
                animalRepository.save(dog.get());
            }
            user.setDataAdopt(LocalDateTime.now());
            userRepository.save(user);
        }
        return messageSender.sendMessage(chatId, "Поздравляем! Вы приютили себя питомца, не забывайте отправлять " +
                "ежедневные отчеты о питомце");
    }
}
