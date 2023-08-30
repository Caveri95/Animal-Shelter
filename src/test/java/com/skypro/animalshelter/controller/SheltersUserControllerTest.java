package com.skypro.animalshelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.service.impl.SheltersUserServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static com.skypro.animalshelter.model.ShelterUserType.JUST_LOOKING;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SheltersUserController.class)
public class SheltersUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SheltersUserServiceImpl sheltersUserServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    static Animal animal = new Animal(1L, "CAT", "Британец", true);
    SheltersUser user = new SheltersUser(6L, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING);
    public static final List<SheltersUser> USERS_LIST = List.of(
            new SheltersUser(6L, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING),
            new SheltersUser(6L, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING),
            new SheltersUser(6L, "editName", "editSurname", "+79210000000", LocalDate.now(), 1L, animal, JUST_LOOKING)
    );

    @Test
    @DisplayName("Добавление пользователя")
    void shouldReturnShelterUserWhenAddUserCalled() throws Exception {

        when(sheltersUserServiceImpl.createUser(user)).thenReturn(user);

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

        verify(sheltersUserServiceImpl, only()).createUser(user);

    }

    @Test
    @DisplayName("Вывод всех пользователей")
    void shouldReturnShelterUserListWhenGetAllUserCalled() throws Exception {
        when(sheltersUserServiceImpl.getAllUsers()).thenReturn(USERS_LIST);
        mvc.perform(MockMvcRequestBuilders.get("/users")
                        .content(objectMapper.writeValueAsString(USERS_LIST))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Вывод пользователя по его id")
    void shouldReturnShelterUserWhenFindByIdCalled() throws Exception {
        when(sheltersUserServiceImpl.findUserById(anyLong())).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/users/{id}", user.getId()))
                .andExpect(status().isOk());

        verify(sheltersUserServiceImpl, only()).findUserById(user.getId());
    }

    @Test
    @DisplayName("Редактирование пользователя")
    void shouldReturnUserWhenEditUserCalled() throws Exception {

        when(sheltersUserServiceImpl.editUser(user)).thenReturn(user);

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

        verify(sheltersUserServiceImpl, only()).editUser(user);

    }



    @Test
    @DisplayName("Удаление пользователя без ошибок")
    void shouldReturnOkWhenDeleteUserByIdCalled() throws Exception {
        when(sheltersUserServiceImpl.deleteUserById(user.getId())).thenReturn(true);
        mvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление пользователя с ошибкой")
    void shouldReturnNotFoundWhenDeleteUserByIdCalled() throws Exception {
        when(sheltersUserServiceImpl.deleteUserById(user.getId())).thenReturn(false);
        mvc.perform(delete("/users/{id}", anyLong()))
                .andExpect(status().isBadRequest());
    }
}