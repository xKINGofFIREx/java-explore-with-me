package ru.controllers;

import dtos.main.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.services.user.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/admin/users")
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                                 @RequestParam(required = false, defaultValue = "0") int from,
                                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return userService.getUsers(ids, from, size);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<Object> removeUserById(@PathVariable("userId") long userId) {
        userService.removeUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
