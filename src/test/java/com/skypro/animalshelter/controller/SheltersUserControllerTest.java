package com.skypro.animalshelter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.ShelterUser;
import com.skypro.animalshelter.service.impl.SheltersUserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SheltersUserController.class)
public class SheltersUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SheltersUserService sheltersUserService;

    @Autowired
    private ObjectMapper objectMapper;

    static Animal animal = new Animal(1L, "CAT", "Британец", true);
    ShelterUser user = new ShelterUser(6L, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal);
    public static final List<ShelterUser> USERS_LIST = List.of(
            new ShelterUser(6L, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal),
            new ShelterUser(6L, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal),
            new ShelterUser(6L, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal)
    );

    @Test
    @DisplayName("Добавление пользователя")
    void shouldReturnShelterUserWhenAddUserCalled() throws Exception {

        when(sheltersUserService.createUser(user)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(6L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("editName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("editSurname"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("+79210000000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataAdopt").value(LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.animal").value(animal))
                .andExpect(status().isOk());

        verify(sheltersUserService, only()).createUser(user);

    }

    @Test
    @DisplayName("Вывод всех пользователей")
    void shouldReturnShelterUserListWhenGetAllUserCalled() throws Exception {
        when(sheltersUserService.getAllUsers()).thenReturn(USERS_LIST);
        mvc.perform(MockMvcRequestBuilders.get("/users")
                        .content(objectMapper.writeValueAsString(USERS_LIST))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Редактирование пользователя")
    void shouldReturnUserWhenEditUserCalled() throws Exception {

        when(sheltersUserService.editUser(user)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(6L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("editName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("editSurname"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("+79210000000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataAdopt").value(LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.animal").value(animal))
                .andExpect(status().isOk());

        verify(sheltersUserService, only()).editUser(user);

    }

    @Test
    @DisplayName("Редактирование пользователя с ошибкой")
    void shouldReturnNotFoundWhenEditUserCalled() throws Exception {

        when(sheltersUserService.editUser(user)).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.put("/users"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Удаление пользователя без ошибок")
    void shouldReturnOkWhenDeleteUserByIdCalled() throws Exception {
        when(sheltersUserService.deleteUserById(user.getId())).thenReturn(true);
        mvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление пользователя с ошибкой")
    void shouldReturnNotFoundWhenDeleteUserByIdCalled() throws Exception {
        when(sheltersUserService.deleteUserById(user.getId())).thenReturn(false);
        mvc.perform(delete("/users/{id}", anyLong()))
                .andExpect(status().isBadRequest());
    }
}