package com.skypro.animalshelter.service;

import com.skypro.animalshelter.exception.ReportNotFoundException;
import com.skypro.animalshelter.exception.ShelterUserNotFoundException;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.impl.SheltersUserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.skypro.animalshelter.model.ShelterUserType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SheltersUserServiceTest {

    @Mock
    private SheltersUserRepository userRepository;
    @InjectMocks
    private SheltersUserServiceImpl userService;

    static Animal animal = new Animal(1L, "CAT", "Британец", true);
    SheltersUser testSheltersUser = new SheltersUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING);


    public static final List<SheltersUser> USERS_LIST = List.of(
            new SheltersUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING),
            new SheltersUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING),
            new SheltersUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING)
    );

    @Test
    @DisplayName("Получить всех пользователей")
    void shouldReturnCollectionOfShelterUserWhenFindAllUserCalled() {
        when(userRepository.findAll())
                .thenReturn(USERS_LIST);

        assertIterableEquals(USERS_LIST, userService.getAllUsers());
    }

    @Test
    @DisplayName("Получить пользователя по его id")
    void shouldReturnShelterUserWhenFindByIdUserCalled() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(testSheltersUser));

        assertEquals(testSheltersUser, userService.findUserById(anyLong()));
    }

    @Test
    @DisplayName("Создание пользователя")
    void shouldReturnUserWhenCreateUserCalled() {
        when(userService.createUser(testSheltersUser)).thenReturn(testSheltersUser);

        assertEquals(userService.createUser(testSheltersUser), testSheltersUser);

    }

    @Test
    @DisplayName("Редактирование пользователя")
    void shouldReturnUserWhenEditUserCalled() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testSheltersUser));

        SheltersUser editSheltersUser = userService.editUser(testSheltersUser);

        Assertions.assertThat(editSheltersUser).isEqualTo(testSheltersUser);
    }

    @Test
    @DisplayName("Удаление пользователя по его id")
    void shouldReturnTrueWhenDeleteUserByIdCalled() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testSheltersUser));

        assertTrue(userService.deleteUserById(anyLong()));
    }

    @Test
    @DisplayName("Ошибка при удалении пользователя")
    void shouldReturnExceptionWhenDeleteAnimalByIdCalled() {
        when(userRepository.findById(anyLong())).thenThrow(ShelterUserNotFoundException.class);

        assertThrows(ShelterUserNotFoundException.class, () -> userService.deleteUserById(anyLong()));
    }

    @Test
    @DisplayName("Ошибка при редактировании пользователя")
    void shouldReturnExceptionWhenEditAnimalCalled() {
        when(userRepository.findById(anyLong())).thenThrow(ShelterUserNotFoundException.class);

        assertThrows(ShelterUserNotFoundException.class, () -> userService.editUser(testSheltersUser));
    }

    @Test
    @DisplayName("Ошибка при поиске пользователя по id")
    void shouldReturnExceptionWhenFindByIdCalled() {
        when(userRepository.findById(anyLong())).thenThrow(ShelterUserNotFoundException.class);

        assertThrows(ShelterUserNotFoundException.class, () -> userService.findUserById(anyLong()));
    }
}
