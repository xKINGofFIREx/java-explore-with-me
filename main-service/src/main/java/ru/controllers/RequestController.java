package ru.controllers;

import dtos.main.request.EventRequestStatusUpdateRequest;
import dtos.main.request.ParticipationRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.exceptions.ConflictException;
import ru.services.request.RequestService;

@RestController
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/users/{userId}/requests")
    public ResponseEntity<Object> getUserRequests(@PathVariable("userId") long userId) {
        return new ResponseEntity<>(requestService.getUserRequest(userId), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/requests")
    public ResponseEntity<Object> createUserRequest(@PathVariable("userId") long userId,
                                                    @RequestParam("eventId") long eventId) throws ConflictException {
        return new ResponseEntity<>(requestService.createUserRequest(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelUserRequest(@PathVariable("userId") long userId,
                                  @PathVariable("requestId") long requestId) {
        return new ResponseEntity<>(requestService.cancelUserRequest(userId, requestId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<ParticipationRequestDto> getUserEventRequests(@PathVariable("userId") long userId,
                                                                        @PathVariable("eventId") long eventId) {

        return new ResponseEntity<>(requestService.getUserEventRequests(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateRequest> updateUserEventRequests(@PathVariable("userId") long userId,
                                                                                   @PathVariable("eventId") long eventId,
                                                                                   @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return new ResponseEntity<>(requestService.updateUserEventRequests(userId, eventId, eventRequestStatusUpdateRequest), HttpStatus.OK);
    }
}
