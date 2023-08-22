package ru.services.request;

import dtos.main.request.EventRequestStatusUpdateRequest;
import dtos.main.request.EventRequestStatusUpdateResult;
import dtos.main.request.ParticipationRequestDto;
import ru.exceptions.ConflictException;
import ru.exceptions.NotFoundException;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getUserRequest(long userId) throws NotFoundException;

    ParticipationRequestDto createUserRequest(long userId, long eventId) throws ConflictException;

    ParticipationRequestDto cancelUserRequest(long userId, long requestId) throws NotFoundException, ConflictException;

    List<ParticipationRequestDto> getUserEventRequests(long userId, long eventId);

    EventRequestStatusUpdateResult updateUserEventRequest(long userId, long eventId, EventRequestStatusUpdateRequest updateRequest) throws NotFoundException, ConflictException;
}
