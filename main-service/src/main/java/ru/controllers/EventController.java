package ru.controllers;

import dtos.main.event.EventFullDto;
import dtos.main.event.EventShortDto;
import dtos.main.request.UpdateEventAdminRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.models.Sort;
import ru.services.event.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<List<EventShortDto>> getEvents(@RequestParam(required = false) String text,
                                                         @RequestParam(required = false) List<Long> categoryIds,
                                                         @RequestParam(required = false) Boolean paid,
                                                         @RequestParam(required = false) LocalDateTime rangeStart,
                                                         @RequestParam(required = false) LocalDateTime rangeEnd,
                                                         @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                                         @RequestParam(required = false) Sort sort,
                                                         @RequestParam(required = false, defaultValue = "0") int from,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(
                eventService.getEvents(text, categoryIds, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size),
                HttpStatus.OK
        );
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable("eventId") long eventId) {
        return new ResponseEntity<>(eventService.getEventById(eventId), HttpStatus.OK);
    }

    @GetMapping("/admin/events")
    public ResponseEntity<List<EventFullDto>> searchEvents(@RequestParam(required = false) List<Long> userIds,
                                                           @RequestParam(required = false) List<String> states,
                                                           @RequestParam(required = false) List<Long> categoryIds,
                                                           @RequestParam(required = false) LocalDateTime rangeStart,
                                                           @RequestParam(required = false) LocalDateTime rangeEnd,
                                                           @RequestParam(required = false, defaultValue = "0") int from,
                                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/admin/events/{eventId}")
    public ResponseEntity<EventFullDto> updateEventById(@PathVariable("eventId") long eventId,
                                                        @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {

        return new ResponseEntity<>(HttpStatus.OK);
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
