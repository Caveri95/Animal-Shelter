package com.skypro.animalshelter.controller;

import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.service.impl.SheltersUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class SheltersUserController {

    private final SheltersUserService userService;

    public SheltersUserController(SheltersUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Добавить нового пользователя", description = "Введите данные")
    @ApiResponse(responseCode = "200", description = "Пользователь добавлен", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))
    })
    public ResponseEntity<SheltersUser> addUser(@RequestBody SheltersUser user) {

        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    @Operation(summary = "Получить список всех юзеров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей получен", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Ничего не найдено"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<List<SheltersUser>> getAllUser() {
        List<SheltersUser> allUsers = userService.getAllUsers();
        if (allUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allUsers);
    }

    @PutMapping
    @Operation(summary = "Отредактировать данные пользователя", description = "Введите id пользователя и его данные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь отредактирован", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "404", description = "Пользователь для редактирования не найден"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
    })
    public ResponseEntity<SheltersUser> editUser(@RequestBody SheltersUser user) {

        SheltersUser sheltersUser = userService.editUser(user);
        if (sheltersUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(sheltersUser);
        }
    }

    @DeleteMapping
    @Operation(summary = "Удалить юзера из базы данных", description = "Необходимо указать id пользователя, которого нужно удалить")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SheltersUser.class)))}),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")

    })
    public ResponseEntity<Void> deleteUser(@RequestParam("id пользователя") Long id) {

        if (userService.deleteUserById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
