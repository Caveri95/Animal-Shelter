package com.skypro.animalshelter.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.Animals;
import com.skypro.animalshelter.model.ShelterInfo;
import com.skypro.animalshelter.model.ShelterUsers;
import com.skypro.animalshelter.repository.AnimalRepository;
import com.skypro.animalshelter.repository.ShelterInfoRepository;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.ButtonReactionService;
import com.skypro.animalshelter.service.MenuService;
import com.skypro.animalshelter.service.TakeAnimal;
import com.skypro.animalshelter.util.CallbackDataRequest;
import com.skypro.animalshelter.util.KeyboardUtil;
import com.skypro.animalshelter.util.MessageSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.skypro.animalshelter.util.CallbackDataRequest.*;

@Service
public class ButtonReactionServiceImpl implements ButtonReactionService {

    private final MenuService menuService;
    private final MessageSender messageSender;
    private final ShelterInfoRepository shelterInfoRepository;
    private final TakeAnimal takeAnimal;


    private boolean isCat = false;

    public ButtonReactionServiceImpl(MenuService menuService, MessageSender messageSender, ShelterInfoRepository shelterInfoRepository, TakeAnimal takeAnimal) {
        this.menuService = menuService;
        this.messageSender = messageSender;
        this.shelterInfoRepository = shelterInfoRepository;
        this.takeAnimal = takeAnimal;
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
                return menuService.getCatMenu(chatId);

            case DOG:
                isCat = false;
                return menuService.getDogMenu(chatId);

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
                return takeAnimal.takeAnimal(chatId, isCat);

            case TAKE_DOG:
                return takeAnimal.takeAnimal(chatId, isCat);

            default:
                return messageSender.sendMessage(chatId, "Такой команды нет, сообщите волонтеру о неисправности");

        }
    }


}
