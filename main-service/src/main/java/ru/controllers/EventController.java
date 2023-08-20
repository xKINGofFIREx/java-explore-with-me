package ru.controllers;

import dtos.main.State;
import dtos.main.event.EventFullDto;
import dtos.main.event.EventShortDto;
import dtos.main.event.NewEventDto;
import dtos.main.request.UpdateEventAdminRequest;
import dtos.main.request.UpdateEventUserRequest;
import dtos.stats.EndpointHitDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.exceptions.ConflictException;
import ru.exceptions.NotFoundException;
import ru.models.Sort;
import ru.services.event.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class EventController {
    private final EventService eventService;
    private final String clientUrl = "http://stats-client:9090/hit";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/events")
    public ResponseEntity<List<EventShortDto>> getEvents(@RequestParam(required = false) String text,
                                                         @RequestParam(required = false) List<Long> categories,
                                                         @RequestParam(required = false) Boolean paid,
                                                         @RequestParam(required = false) String rangeStart,
                                                         @RequestParam(required = false) String rangeEnd,
                                                         @RequestParam(required = false, defaultValue = "false") boolean onlyAvailable,
                                                         @RequestParam(required = false) Sort sort,
                                                         @RequestParam(required = false, defaultValue = "0") int from,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {

        restTemplate.postForEntity(clientUrl,
                new EndpointHitDto("ewm-main-service", "/events", ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest().getRemoteAddr(),
                        LocalDateTime.now()), EndpointHitDto.class);
        return new ResponseEntity<>(
                eventService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size),
                HttpStatus.OK);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable("eventId") long eventId) throws NotFoundException {
        restTemplate.postForObject(clientUrl,
                new EndpointHitDto("ewm-main-service", "/events/" + eventId, ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest().getRemoteAddr(),
                        LocalDateTime.now()), EndpointHitDto.class);
        return new ResponseEntity<>(eventService.getEventById(eventId), HttpStatus.OK);
    }

    @GetMapping("/admin/events")
    public ResponseEntity<List<EventFullDto>> searchEvents(@RequestParam(required = false) List<Long> users,
                                                           @RequestParam(required = false) List<State> states,
                                                           @RequestParam(required = false) List<Long> categories,
                                                           @RequestParam(required = false) String rangeStart,
                                                           @RequestParam(required = false) String rangeEnd,
                                                           @RequestParam(required = false, defaultValue = "0") int from,
                                                           @RequestParam(required = false, defaultValue = "10") int size) {

        return new ResponseEntity<>(
                eventService.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size),
                HttpStatus.OK);
    }

    @PatchMapping("/admin/events/{eventId}")
    public ResponseEntity<EventFullDto> updateEventById(@PathVariable("eventId") long eventId,
                                                        @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) throws NotFoundException, ConflictException {

        return new ResponseEntity<>(eventService.updateEventById(eventId, updateEventAdminRequest), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/events")
    public ResponseEntity<List<EventShortDto>> getUserEvents(@PathVariable("userId") long userId,
                                                             @RequestParam(required = false, defaultValue = "0") int from,
                                                             @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(eventService.getUserEvents(userId, from, size), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/events")
    public ResponseEntity<EventFullDto> createUserEvent(@PathVariable("userId") long userId,
                                                        @Valid @RequestBody NewEventDto newEventDto) throws ConflictException {
        return new ResponseEntity<>(eventService.createUserEvent(userId, newEventDto), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> getUserEventById(@PathVariable("userId") long userId,
                                                         @PathVariable("eventId") long eventId) throws NotFoundException {
        return new ResponseEntity<>(eventService.getUserEventById(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> updateUserEventById(@PathVariable("userId") long userId,
                                                            @PathVariable("eventId") long eventId,
                                                            @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) throws ConflictException, NotFoundException {
        return new ResponseEntity<>(eventService.updateUserEventById(userId, eventId, updateEventUserRequest), HttpStatus.OK);
    }
}
