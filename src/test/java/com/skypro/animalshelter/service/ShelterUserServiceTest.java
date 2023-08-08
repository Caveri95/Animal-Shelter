package com.skypro.animalshelter.service;

import com.skypro.animalshelter.model.SheltersUser;
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
public class ShelterUserServiceTest {

    @Mock
    private SheltersUserRepository userRepository;
    @InjectMocks
    private SheltersUserService userService;

    SheltersUser testUser = new SheltersUser(5, "testName", "testSurname", "test_012345");


    public static final List<SheltersUser> USERS_LIST = List.of(
            new SheltersUser(1, "name1", "surname1", "1"),
            new SheltersUser(2, "name2", "surname2", "2"),
            new SheltersUser(3, "name3", "surname3", "3"),
            new SheltersUser(4, "name4", "surname4", "4")
    );

    @Test
    void shouldReturnCollectionOfShelterUserWhenFindAllUserCalled() {
        when(userRepository.findAll())
                .thenReturn(USERS_LIST);

        assertIterableEquals(USERS_LIST, userService.getAllUsers());
    }

    @Test
    void shouldReturnUserWhenCreateUserCalled() {
        when(userService.createUser(testUser)).thenReturn(testUser);

        assertEquals(userService.createUser(testUser), testUser);

    }

    @Test
    void shouldReturnUserWhenEditUserCalled() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        SheltersUser editUser = userService.editUser(testUser);

        Assertions.assertThat(editUser).isEqualTo(testUser);
    }

    @Test
    void shouldReturnTrueWhenDeleteUserByIdCalled() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        assertTrue(userService.deleteUserById(anyLong()));
    }
}
