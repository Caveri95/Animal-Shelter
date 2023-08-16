package com.skypro.animalshelter.controller;

import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal")
@Tag(name = "Животные приюта", description = "CRUD операции и др.эндпоинты для работы с животными")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    @Operation(summary = "Добавить новое животное", description = "Введите данные")
    @ApiResponse(responseCode = "200", description = "Животное добавлено", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Animal.class)))
    })
    public ResponseEntity<Animal> addAnimal(@RequestBody Animal animal) {

        return ResponseEntity.ok(animalService.createAnimal(animal));
    }

    @GetMapping
    @Operation(summary = "Получить список всех животных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список животных получен", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Animal.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Ничего не найдено"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<List<Animal>> getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        if (animals.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить животного по его id", description = "Введите id животного")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Животное получен", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "404", description = "Животное не найден"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<Animal> getAnimalById(@PathVariable long id) {

        Animal animalById = animalService.findAnimalById(id);
        if (animalById == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(animalById);
        }
    }

    @PutMapping
    @Operation(summary = "Отредактировать животного", description = "Введите id животного и его данные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Животное отредактировано", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Animal.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "404", description = "Животное для редактирования не найдено"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<Animal> editAnimal(@RequestBody Animal animals) {

        Animal animal = animalService.editAnimal(animals);
        if (animal == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(animal);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить животное из базы данных", description = "Необходимо указать id животного, которого нужно удалить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Животное удалено", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Animal.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")

    })
    public ResponseEntity<Void> deleteAnimal(@PathVariable long id) {

        if (animalService.deleteAnimalById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
