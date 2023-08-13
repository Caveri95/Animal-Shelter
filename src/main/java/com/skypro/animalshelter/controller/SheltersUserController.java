package com.skypro.animalshelter.controller;

import com.skypro.animalshelter.model.ShelterUser;
import com.skypro.animalshelter.service.impl.SheltersUserServiceImpl;
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
@RequestMapping("/users")
@Tag(name = "Пользователи приюта", description = "CRUD операции и др.эндпоинты для работы с пользователями")
public class SheltersUserController {

    private final SheltersUserServiceImpl userService;

    public SheltersUserController(SheltersUserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Добавить нового пользователя", description = "Введите данные")
    @ApiResponse(responseCode = "200", description = "Пользователь добавлен", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShelterUser.class)))
    })
    public ResponseEntity<ShelterUser> addUser(@RequestBody ShelterUser shelterUser) {

        return ResponseEntity.ok(userService.createUser(shelterUser));
    }

    @GetMapping
    @Operation(summary = "Получить список всех юзеров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей получен", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShelterUser.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Ничего не найдено"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<List<ShelterUser>> getAllUser() {
        List<ShelterUser> allShelterUsers = userService.getAllUsers();
        if (allShelterUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allShelterUsers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по его id", description = "Введите id пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь получен", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShelterUser.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<ShelterUser> getUserById(@PathVariable long id) {

        ShelterUser sheltersShelterUser = userService.findUserById(id);
        if (sheltersShelterUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(sheltersShelterUser);
        }
    }

    @PutMapping
    @Operation(summary = "Отредактировать данные пользователя", description = "Введите id пользователя и его данные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь отредактирован", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShelterUser.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "404", description = "Пользователь для редактирования не найден"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<ShelterUser> editUser(@RequestBody ShelterUser shelterUser) {

        ShelterUser sheltersShelterUser = userService.editUser(shelterUser);
        if (sheltersShelterUser == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(sheltersShelterUser);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить юзера из базы данных", description = "Необходимо указать id пользователя, которого нужно удалить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShelterUser.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")

    })
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {

        if (userService.deleteUserById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
