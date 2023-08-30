package com.skypro.animalshelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.service.AnimalService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

    static Animal animalTest = new Animal(1L, "CAT", "Британец", true);

    List<Animal> list = new ArrayList<>(List.of(new Animal(1L, "DOG", "Овчарка", true),
            new Animal(2L, "CAT", "Рыжий", true),
            new Animal(3L, "DOG", "Шпиц", true)));

    @Test
    @DisplayName("Добавление животного")
    void shouldReturnAnimalWhenAddUserCalled() throws Exception {

        when(animalService.createAnimal(animalTest)).thenReturn(animalTest);

        mvc.perform(MockMvcRequestBuilders.post("/animal")
                        .content(objectMapper.writeValueAsString(animalTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeAnimal").value("CAT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed").value("Британец"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inShelter").value(true))
                .andExpect(status().isOk());

        verify(animalService, only()).createAnimal(animalTest);
    }

    @Test
    @DisplayName("Получить всех животных")
    void shouldReturnAllAnimalWhenGetAllAnimalCalled() throws Exception {

        when(animalService.getAllAnimals()).thenReturn(list);

        mvc.perform(MockMvcRequestBuilders.get("/animal")
                        .content(objectMapper.writeValueAsString(list))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(status().isOk());

        verify(animalService, only()).getAllAnimals();
    }

    @Test
    @DisplayName("Вывод животного по его id")
    void shouldReturnAnimalWhenFindByIdCalled() throws Exception {
        when(animalService.findAnimalById(anyLong())).thenReturn(animalTest);
        mvc.perform(MockMvcRequestBuilders.get("/animal/{id}", animalTest.getId()))
                .andExpect(status().isOk());

        verify(animalService, only()).findAnimalById(animalTest.getId());
    }

    @Test
    @DisplayName("Редактирование животного")
    void shouldReturnEditAnimalWhenEditAnimalCalled() throws Exception {

        when(animalService.editAnimal(animalTest)).thenReturn(animalTest);

        mvc.perform(MockMvcRequestBuilders.put("/animal")
                        .content(objectMapper.writeValueAsString(animalTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeAnimal").value("CAT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed").value("Британец"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inShelter").value(true))
                .andExpect(status().isOk());

        verify(animalService, only()).editAnimal(animalTest);
    }

    @Test
    @DisplayName("Удаление животного")
    void shouldReturnOkWhenDeleteAnimalByIdCalled() throws Exception {
        when(animalService.deleteAnimalById(anyLong())).thenReturn(true);
        mvc.perform(delete("/animal/{id}", anyLong()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление животного с ошибкой")
    void shouldReturnNotFoundWhenDeleteAnimalByIdCalled() throws Exception {
        when(animalService.deleteAnimalById(1L)).thenReturn(false);
        mvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }







}
