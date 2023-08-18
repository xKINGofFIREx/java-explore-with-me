package ru.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @GetMapping("/events")
    public void getEvents() {

    }

    @GetMapping("/events/{eventId}")
    public void getEventById(@PathVariable("eventId") long eventId) {

    }

    @GetMapping("/admin/events")
    public void searchEvents() {

    }

    @PatchMapping("/admin/events/{eventId}")
    public void updateEventById(@PathVariable("eventId") long eventId) {

    }

    @GetMapping("/users/{userId}/events")
    public void getUserEvents(@PathVariable("userId") long userId) {

    }

    @PostMapping("/users/{userId}/events")
    public void createUserEvent(@PathVariable("userId") long userId) {

    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public void getUserEventById(@PathVariable("userId") long userId,
                                 @PathVariable("eventId") long eventId) {

    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public void updateUserEventById(@PathVariable("userId") long userId,
                                    @PathVariable("eventId") long eventId) {

    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public void getUserEventRequests(@PathVariable("userId") long userId,
                                     @PathVariable("eventId") long eventId) {

    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public void updateUserEventRequests(@PathVariable("userId") long userId,
                                        @PathVariable("eventId") long eventId) {

    }

}
