package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.ShelterUser;
import com.skypro.animalshelter.repository.AnimalRepository;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.TakeAnimal;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            return messageSender.sendMessage(chatId, "Ппрежде чем выбрать питомца, пожалуйста, оставьте " +
                    "свои контактные данные в формате \"Имя Фамилия Номер телефона с кодом +7\"");
        }

        if (userRepository.findSheltersUserByChatId(chatId).isPresent()) {
            if (userRepository.findSheltersUserByChatId(chatId).get().getAnimal() != null) {
                return messageSender.sendMessage(chatId, "Наш приют не разрешает забрать больше одного питомца.");
            }

            ShelterUser user = userRepository.findSheltersUserByChatId(chatId).get();
            if (isCat) {
                Optional<Animal> cat = animalRepository.findAnimalByTypeAnimal(CAT.getCallbackData()).stream().filter(Animal::getInShelter).findAny();
                if (cat.isEmpty()) {
                    return messageSender.sendMessage(chatId, "Извините, сейчас в приюте нет кошек.");
                }
                user.setAnimal(cat.get());
                cat.get().setInShelter(false);
                animalRepository.save(cat.get());


            } else {
                Optional<Animal> dog = animalRepository.findAnimalByTypeAnimal(DOG.getCallbackData()).stream().filter(Animal::getInShelter).findAny();
                if (dog.isEmpty()) {
                    return messageSender.sendMessage(chatId, "Извините, сейчас в приюте нет собак.");
                }
                user.setAnimal(dog.get());
                dog.get().setInShelter(false);
                animalRepository.save(dog.get());
            }
            user.setDataAdopt(LocalDate.now());
            userRepository.save(user);
        }
        return messageSender.sendMessage(chatId, "Поздравляем! Вы приютили питомца и обрели верного друга! Не забывайте ежедневно отправлять " +
                "отчеты о своем питомце");
    }
}
