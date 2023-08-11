package com.skypro.animalshelter.service;

import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.ShelterUser;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.impl.SheltersUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterShelterUserServiceTest {

    @Mock
    private SheltersUserRepository userRepository;
    @InjectMocks
    private SheltersUserService userService;

    static Animal animal = new Animal(1L, "CAT", "Британец", true);
    ShelterUser testShelterUser = new ShelterUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal);


    public static final List<ShelterUser> USERS_LIST = List.of(
            new ShelterUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal),
            new ShelterUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal),
            new ShelterUser(6, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal)
    );

    @Test
    void shouldReturnCollectionOfShelterUserWhenFindAllUserCalled() {
        when(userRepository.findAll())
                .thenReturn(USERS_LIST);

        assertIterableEquals(USERS_LIST, userService.getAllUsers());
    }

    @Test
    void shouldReturnUserWhenCreateUserCalled() {
        when(userService.createUser(testShelterUser)).thenReturn(testShelterUser);

        assertEquals(userService.createUser(testShelterUser), testShelterUser);

    }

    @Test
    void shouldReturnUserWhenEditUserCalled() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testShelterUser));

        ShelterUser editShelterUser = userService.editUser(testShelterUser);

        Assertions.assertThat(editShelterUser).isEqualTo(testShelterUser);
    }

    /*@Test
    void shouldReturnTrueWhenDeleteUserByIdCalled() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testShelterUser));

        assertTrue(userService.deleteUserById(anyLong()));
    }*/
}
