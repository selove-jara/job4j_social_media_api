package ru.job4j.social.media.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.social.media.api.model.User;
import ru.job4j.social.media.api.service.UserService;

import java.util.List;

@Tag(name = "UsersController", description = "UsersController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll() {
        return userService.findAll();
    }
}
