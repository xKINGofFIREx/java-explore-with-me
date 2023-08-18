package ru.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/admin/users")
    public void getUsers() {

    }

    @PostMapping("/admin/users")
    public void createUser() {

    }

    @DeleteMapping("/admin/users/{userId}")
    public void removeUserById(@PathVariable("userId") long userId) {

    }
}
