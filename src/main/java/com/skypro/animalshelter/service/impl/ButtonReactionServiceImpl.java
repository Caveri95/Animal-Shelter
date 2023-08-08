package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.Animals;
import com.skypro.animalshelter.model.ShelterInfo;
import com.skypro.animalshelter.model.ShelterUsers;
import com.skypro.animalshelter.repository.AnimalRepository;
import com.skypro.animalshelter.repository.ShelterInfoRepository;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.ButtonReactionService;
import com.skypro.animalshelter.service.MenuService;
import com.skypro.animalshelter.util.CallbackDataRequest;
import com.skypro.animalshelter.util.KeyboardUtil;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.skypro.animalshelter.util.CallbackDataRequest.*;

@Service
public class ButtonReactionServiceImpl implements ButtonReactionService {

    private final TelegramBot telegramBot;
    private final MenuService menuService;
    private final KeyboardUtil keyboardUtil;
    private final MessageSender messageSender;
    private final ShelterInfoRepository shelterInfoRepository;
    private final AnimalRepository animalRepository;
    private final SheltersUserRepository userRepository;


    private boolean isCat = false;

    public ButtonReactionServiceImpl(TelegramBot telegramBot, MenuService menuService, KeyboardUtil keyboardUtil, MessageSender messageSender, ShelterInfoRepository shelterInfoRepository, AnimalRepository animalRepository, SheltersUserRepository userRepository) {
        this.telegramBot = telegramBot;
        this.menuService = menuService;
        this.keyboardUtil = keyboardUtil;
        this.messageSender = messageSender;
        this.shelterInfoRepository = shelterInfoRepository;
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage buttonReaction(CallbackQuery callbackQuery) {

        Long chatId = callbackQuery.message().chat().id();
        String data = callbackQuery.data();
        CallbackDataRequest constantByRequest = CallbackDataRequest.getConstantByRequest(data); // Здесь ищем нужную константу в зависимости от пришедшего callbackQuery
        Optional<ShelterInfo> shelterInfo;

        if (isCat) {
            shelterInfo = shelterInfoRepository.findById(1L);
        } else {
            shelterInfo = shelterInfoRepository.findById(2L);
        }

        switch (constantByRequest) {

            case CAT:
                isCat = true;

                InlineKeyboardMarkup keyboard = keyboardUtil.setKeyboard(
                        GENERAL_SHELTER_INFO,
                        HOW_TO_TAKE_ANIMAL,
                        REPORT_ANIMAL,
                        TAKE_CAT,
                        VOLUNTEER);
                return messageSender.sendMessageWithKeyboard(chatId, "Вы выбрали приют для кошек, чем могу помочь?", keyboard);

            case DOG:
                isCat = false;

                InlineKeyboardMarkup keyboard1 = keyboardUtil.setKeyboard(    // Чтобы каждый раз по разному не называть можно в метод replyMarkup сразу вставлять или думать над классом клавиатуры
                        GENERAL_SHELTER_INFO,
                        HOW_TO_TAKE_ANIMAL,
                        REPORT_ANIMAL,
                        TAKE_DOG,
                        VOLUNTEER);
                return messageSender.sendMessageWithKeyboard(chatId, "Вы выбрали приют для собак, чем могу помочь?", keyboard1);

            case GENERAL_SHELTER_INFO:
                return menuService.getInfoAboutShelter(chatId);

            case ABOUT_SHELTER:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getAboutShelter());
                }

            case CONTACT_SHELTER:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getAddressAndSchedule());
                }

            case SAFETY_CONTACT_FOR_CAR_PASS:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getSafetyContactForCarPass());
                }

            case SAFETY_IN_SHELTER_TERRITORY:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getSafetyOnTerritory());
                }

            case GIVE_MY_CONTACT:
                return messageSender.sendMessage(chatId, "Введите ваши данные в формате \"Имя Фамилия номер телефона с кодом +7\" ");

            case CALL_VOLUNTEER:
                return messageSender.sendMessage(chatId, "Позвать волонтера, наверно отдельный какой то чат с волонтером");

            case ROLLBACK:
                return menuService.getStartMenuShelter(chatId); //Тут возврат в меню выбора кошек/собак, надо поменять на следующий шаг

            case HOW_TO_TAKE_ANIMAL:
                return menuService.getInfoAboutTakeAnimal(chatId, isCat);

            case SHELTER_RULES_BEFORE_MEETING_ANIMAL:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getFirstMeetRecommendation());
                }
            case DOCUMENTS_TO_TAKE_ANIMAL:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getDocuments());
                }
            case TRANSPORTATION_ADVICE:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getTransportationAdvice());
                }
            case HOUSE_RULES_FOR_SMALL_ANIMAL:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getHouseRulesForSmallAnimal());
                }
            case HOUSE_RULES_FOR_ADULT_ANIMAL:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getHouseRulesForAdultAnimal());
                }
            case HOUSE_RULES_FOR_ANIMAL_WITH_DISABILITY:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getRulesForAnimalWithDisability());
                }
            case CYNOLOGIST_ADVICE:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getCynologistAdvice());
                }
            case CYNOLOGISTS:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getCynologists());
                }
            case REFUSE_REASONS:
                if (shelterInfo.isPresent()) {
                    return messageSender.sendMessage(chatId, shelterInfo.get().getRefuseReasons());
                }
            case TAKE_CAT:
            case TAKE_DOG:

                if (userRepository.findSheltersUserByChatId(chatId).isEmpty()) {
                    return messageSender.sendMessage(chatId, "Пожалуйста, прежде чем взять себе питомца оставьте" +
                            "свои контактные данные в формате \"Имя Фамилия Номер телефона с кодом +7\"");
                }

                if (userRepository.findSheltersUserByChatId(chatId).isPresent()) {
                    if (userRepository.findSheltersUserByChatId(chatId).get().getAnimals() != null) {
                        return messageSender.sendMessage(chatId, "Больше одного животного в нашем приюте брать нельзя");
                    }

                    ShelterUsers user = userRepository.findSheltersUserByChatId(chatId).get();
                    if (isCat) {
                        Optional<Animals> cat = animalRepository.findAnimalByTypeAnimal(CAT.getText()).stream().filter(animals -> animals.getInShelter()).findAny();
                        if (cat.isEmpty()) {
                            return messageSender.sendMessage(chatId, "Извините, сейчас в приюте нет котов");
                        }
                        user.setAnimals(cat.get());
                        cat.get().setInShelter(false);
                        animalRepository.save(cat.get());


                    } else {
                        Optional<Animals> dog = animalRepository.findAnimalByTypeAnimal(DOG.getText()).stream().filter(animals -> animals.getInShelter()).findAny();
                        if (dog.isEmpty()) {
                            return messageSender.sendMessage(chatId, "Извините, сейчас в приюте нет собак");
                        }
                        user.setAnimals(dog.get());
                        dog.get().setInShelter(false);
                        animalRepository.save(dog.get());
                    }
                    user.setDataAdopt(LocalDateTime.now());
                    userRepository.save(user);
                    return messageSender.sendMessage(chatId, "Поздравляем! Вы приютили себя питомца, не забывайте отправлять " +
                            "ежедневные отчеты о питомце");
                }

            default:
                return messageSender.sendMessage(chatId, "Позвать волонтера");

        }
    }


}
