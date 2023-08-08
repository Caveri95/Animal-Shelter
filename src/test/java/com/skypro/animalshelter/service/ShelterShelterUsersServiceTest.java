package com.skypro.animalshelter.service;

import com.skypro.animalshelter.model.ShelterUsers;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.impl.SheltersUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterShelterUsersServiceTest {

    @Mock
    private SheltersUserRepository userRepository;
    @InjectMocks
    private SheltersUserService userService;

    ShelterUsers testShelterUsers = new ShelterUsers(5, "testName", "testSurname", "test_012345");


    public static final List<ShelterUsers> USERS_LIST = List.of(
            new ShelterUsers(1, "name1", "surname1", "1"),
            new ShelterUsers(2, "name2", "surname2", "2"),
            new ShelterUsers(3, "name3", "surname3", "3"),
            new ShelterUsers(4, "name4", "surname4", "4")
    );

    @Test
    void shouldReturnCollectionOfShelterUserWhenFindAllUserCalled() {
        when(userRepository.findAll())
                .thenReturn(USERS_LIST);

        assertIterableEquals(USERS_LIST, userService.getAllUsers());
    }

    @Test
    void shouldReturnUserWhenCreateUserCalled() {
        when(userService.createUser(testShelterUsers)).thenReturn(testShelterUsers);

        assertEquals(userService.createUser(testShelterUsers), testShelterUsers);

    }

    @Test
    void shouldReturnUserWhenEditUserCalled() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testShelterUsers));

        ShelterUsers editShelterUsers = userService.editUser(testShelterUsers);

        Assertions.assertThat(editShelterUsers).isEqualTo(testShelterUsers);
    }

    @Test
    void shouldReturnTrueWhenDeleteUserByIdCalled() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testShelterUsers));

        assertTrue(userService.deleteUserById(anyLong()));
    }
}
