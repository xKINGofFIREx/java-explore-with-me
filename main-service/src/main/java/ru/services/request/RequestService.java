package ru.services.request;

import dtos.main.request.EventRequestStatusUpdateRequest;
import dtos.main.request.ParticipationRequestDto;
import ru.exceptions.ConflictException;

public interface RequestService {
    ParticipationRequestDto getUserRequest(long userId);

    ParticipationRequestDto createUserRequest(long userId, long eventId) throws ConflictException;

    ParticipationRequestDto cancelUserRequest(long userId, long requestId);

    ParticipationRequestDto getUserEventRequests(long userId, long eventId);

    EventRequestStatusUpdateRequest updateUserEventRequests(long userId, long eventId, EventRequestStatusUpdateRequest request);
}
