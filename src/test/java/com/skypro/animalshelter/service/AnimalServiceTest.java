package com.skypro.animalshelter.service;

import com.skypro.animalshelter.exception.AnimalNotFoundException;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.repository.AnimalRepository;
import com.skypro.animalshelter.service.impl.AnimalServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;
    @InjectMocks
    private AnimalServiceImpl animalService;

    Animal animal = new Animal(1L, "CAT", "Британец", true);

    public static final List<Animal> ANIMAL_LIST = List.of(
            new Animal(1L, "CAT", "Британец", true),
            new Animal(2L, "CAT", "Британец1", true),
            new Animal(3L, "CAT", "Британец2", true)
    );

    @Test
    @DisplayName("Получить всех животных")
    void shouldReturnCollectionOfAnimalWhenFindAllUserCalled() {
        when(animalRepository.findAll())
                .thenReturn(ANIMAL_LIST);

        assertIterableEquals(ANIMAL_LIST, animalService.getAllAnimals());
    }

    @Test
    @DisplayName("Получить животного по его id")
    void shouldReturnAnimalWhenFindByIdCalled() {
        when(animalRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(animal));

        assertEquals(animal, animalService.findAnimalById(anyLong()));
    }



    @Test
    @DisplayName("Создание животного")
    void shouldReturnAnimalWhenCreateUserCalled() {
        when(animalService.createAnimal(animal)).thenReturn(animal);

        assertEquals(animalService.createAnimal(animal), animal);

    }

    @Test
    @DisplayName("Редактирование животного")
    void shouldReturnAnimalWhenEditAnimalCalled() {

        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal));

        Animal animal1 = animalService.editAnimal(animal);

        Assertions.assertThat(animal1).isEqualTo(animal);
    }



    @Test
    @DisplayName("Удаление животного по его id")
    void shouldReturnTrueWhenDeleteAnimalByIdCalled() {

        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal));

        assertTrue(animalService.deleteAnimalById(anyLong()));
    }

    @Test
    @DisplayName("Ошибка при удалении животного")
    void shouldReturnExceptionWhenDeleteAnimalByIdCalled() {
        when(animalRepository.findById(anyLong())).thenThrow(AnimalNotFoundException.class);

        assertThrows(AnimalNotFoundException.class, () -> animalService.deleteAnimalById(anyLong()));
    }

    @Test
    @DisplayName("Ошибка при редактировании животного")
    void shouldReturnExceptionWhenEditAnimalCalled() {
        when(animalRepository.findById(anyLong())).thenThrow(AnimalNotFoundException.class);

        assertThrows(AnimalNotFoundException.class, () -> animalService.editAnimal(animal));
    }

    @Test
    @DisplayName("Ошибка при поиске животного по id")
    void shouldReturnExceptionWhenFindByIdCalled() {
        when(animalRepository.findById(anyLong())).thenThrow(AnimalNotFoundException.class);

        assertThrows(AnimalNotFoundException.class, () -> animalService.findAnimalById(anyLong()));
    }
}
