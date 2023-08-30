package com.skypro.animalshelter.controller;

import com.skypro.animalshelter.model.SheltersUser;
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
@Tag(name = "Пользователи приюта", description = "CRUD-операции и другие эндпоинты для работы с пользователями")
public class SheltersUserController {

    private final SheltersUserServiceImpl userService;
    public SheltersUserController(SheltersUserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Добавить нового пользователя", description = "Введите данные")
    @ApiResponse(responseCode = "200", description = "Пользователь добавлен", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))
    })
    public ResponseEntity<SheltersUser> addUser(@RequestBody SheltersUser sheltersUser) {

        return ResponseEntity.ok(userService.createUser(sheltersUser));
    }

    @GetMapping
    @Operation(summary = "Получить список всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей получен", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Ничего не найдено"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<List<SheltersUser>> getAllUser() {
        List<SheltersUser> allSheltersUsers = userService.getAllUsers();
        if (allSheltersUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allSheltersUsers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по его id", description = "Введите id пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<SheltersUser> getUserById(@PathVariable long id) {

        SheltersUser sheltersSheltersUser = userService.findUserById(id);
        if (sheltersSheltersUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(sheltersSheltersUser);
        }
    }

    @PutMapping
    @Operation(summary = "Отредактировать данные пользователя", description = "Введите id пользователя и новые данные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные о пользователе отредактированы", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<SheltersUser> editUser(@RequestBody SheltersUser sheltersUser) {

        SheltersUser sheltersSheltersUser = userService.editUser(sheltersUser);
        if (sheltersSheltersUser == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(sheltersSheltersUser);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя из базы данных", description = "Необходимо указать id пользователя, которого нужно удалить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))}),
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
