package ru.controllers;

import dtos.main.request.EventRequestStatusUpdateRequest;
import dtos.main.request.EventRequestStatusUpdateResult;
import dtos.main.request.ParticipationRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.exceptions.ConflictException;
import ru.exceptions.NotFoundException;
import ru.services.request.RequestService;

import java.util.List;

@RestController
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/users/{userId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getUserRequests(@PathVariable("userId") long userId) throws NotFoundException {
        return new ResponseEntity<>(requestService.getUserRequest(userId), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/requests")
    public ResponseEntity<ParticipationRequestDto> createUserRequest(@PathVariable("userId") long userId,
                                                    @RequestParam("eventId") long eventId) throws ConflictException {
        return new ResponseEntity<>(requestService.createUserRequest(userId, eventId), HttpStatus.CREATED);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelUserRequest(@PathVariable("userId") long userId,
                                  @PathVariable("requestId") long requestId) throws NotFoundException, ConflictException {
        return new ResponseEntity<>(requestService.cancelUserRequest(userId, requestId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getUserEventRequests(@PathVariable("userId") long userId,
                                                                              @PathVariable("eventId") long eventId) {

        return new ResponseEntity<>(requestService.getUserEventRequests(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateUserEventRequest(@PathVariable("userId") long userId,
                                                                                 @PathVariable("eventId") long eventId,
                                                                                 @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) throws NotFoundException, ConflictException {
        return new ResponseEntity<>(requestService.updateUserEventRequest(userId, eventId, eventRequestStatusUpdateRequest), HttpStatus.OK);
    }
}
