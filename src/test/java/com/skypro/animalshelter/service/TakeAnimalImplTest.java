package com.skypro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.impl.TakeAnimalImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.skypro.animalshelter.model.ShelterUserType.JUST_LOOKING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TakeAnimalImplTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private SheltersUserRepository userRepository;
    @Captor
    private ArgumentCaptor<SendMessage> captor;

    @InjectMocks
    private TakeAnimalImpl takeAnimal;

    static Animal animal = new Animal(1L, "CAT", "Британец", true);
    SheltersUser testSheltersUser = new SheltersUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING);
    @Test
    @DisplayName("Оставить контактные данные прежде чем взять питомца")
    void takeAnimalWithoutContact() {

        String text = "Пожалуйста, прежде чем взять себе питомца оставьте " +
                "свои контактные данные в формате \"Имя Фамилия Номер телефона с кодом +7\"";
        Long id = 1L;

        when(userRepository.findSheltersUserByChatId(anyLong())).thenReturn(Optional.empty());
        takeAnimal.takeAnimal(id, true);


        verify(telegramBot, only()).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
    }

    @Test
    @DisplayName("Проверка есть ли у посетителя животное из этого приюта")
    void takeAnimal() {

        String text = "Больше одного животного в нашем приюте брать нельзя";
        Long id = 1L;

        when(userRepository.findSheltersUserByChatId(anyLong())).thenReturn(Optional.ofNullable(testSheltersUser));

        takeAnimal.takeAnimal(id, true);

        verify(telegramBot, only()).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
    }

}