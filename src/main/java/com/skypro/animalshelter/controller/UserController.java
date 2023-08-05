package com.skypro.animalshelter.controller;

import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.service.impl.SheltersUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final SheltersUserService userService;

    public UserController(SheltersUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Добавить нового пользователя", description = "Введите данные")
    public ResponseEntity<SheltersUser> addUser(@RequestBody SheltersUser user) {

        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    @Operation(summary = "Получить список всех юзеров")
    public ResponseEntity<List<SheltersUser>> getAllUser() {
        List<SheltersUser> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
