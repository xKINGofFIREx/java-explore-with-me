package ru.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class RequestController {

    @GetMapping("/users/{userId}/requests")
    public void getUserRequests(@PathVariable("userId") long userId) {

    }

    @PostMapping("/users/{userId}/requests")
    public void createUserRequest(@PathVariable("userId") long userId) {

    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public void cancelUserRequest(@PathVariable("userId") long userId,
                                  @PathVariable("requestId") long requestId) {

    }
}
