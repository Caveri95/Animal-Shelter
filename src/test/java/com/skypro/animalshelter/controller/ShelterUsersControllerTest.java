package com.skypro.animalshelter.controller;

import com.skypro.animalshelter.model.ShelterUsers;
import com.skypro.animalshelter.service.impl.SheltersUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.skypro.animalshelter.service.ShelterShelterUsersServiceTest.USERS_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterUsersControllerTest {

    @Mock
    private SheltersUserService userService;


    @InjectMocks
    private SheltersUserController sheltersUserController;

    ShelterUsers shelterUsers = new ShelterUsers(6, "editName", "editSurname", "test_987654");


    @Test
    @DisplayName("Добавление пользователя")
    void shouldReturnTrueWhenAddUserCalled() {

        when(userService.createUser(shelterUsers)).thenReturn(shelterUsers);

        ResponseEntity<ShelterUsers> sheltersUserResponseEntity = sheltersUserController.addUser(shelterUsers);

        Assertions.assertEquals(shelterUsers, sheltersUserResponseEntity.getBody());

        verify(userService, only()).createUser(shelterUsers);

    }

    @Test
    @DisplayName("Вывод всех пользователей")
    void shouldReturnListOfSheltersUserWhenGetAllUserCalled() {
        when(userService.getAllUsers()).thenReturn(USERS_LIST);

        ResponseEntity<List<ShelterUsers>> allUser = sheltersUserController.getAllUser();

        assertIterableEquals(allUser.getBody(), USERS_LIST);

        verify(userService, only()).getAllUsers();
    }

    @Test
    @DisplayName("Редактирование пользователя")
    void shouldReturnUserWhenEditUserCalled() {

        when(userService.editUser(shelterUsers)).thenReturn(shelterUsers);

        ResponseEntity<ShelterUsers> sheltersUserResponseEntity = sheltersUserController.editUser(shelterUsers);

        assertEquals(sheltersUserResponseEntity.getBody(), shelterUsers);

        verify(userService, only()).editUser(shelterUsers);
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUser() {

        when(userService.deleteUserById(anyLong())).thenReturn(true);

        ResponseEntity<Void> voidResponseEntity = sheltersUserController.deleteUser(anyLong());

        Assertions.assertEquals(HttpStatus.OK, voidResponseEntity.getStatusCode());
        verify(userService, only()).deleteUserById(anyLong());
    }
}
