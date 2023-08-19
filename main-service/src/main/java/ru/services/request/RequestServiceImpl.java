package ru.services.request;

import dtos.main.Status;
import dtos.main.request.EventRequestStatusUpdateRequest;
import dtos.main.request.ParticipationRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.exceptions.ConflictException;
import ru.mappers.RequestMapper;
import ru.models.Event;
import ru.models.Request;
import ru.models.User;
import ru.repositories.EventRepository;
import ru.repositories.RequestRepository;
import ru.repositories.UserRepository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public ParticipationRequestDto getUserRequest(long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto createUserRequest(long userId, long eventId) throws ConflictException {
        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId))
            throw new ConflictException("Нельзя добавить повторный запрос");

        Event event = eventRepository.getReferenceById(eventId);
        User user = userRepository.getReferenceById(userId);

        if (event.getInitiator().getId() == userId)
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии");
        if (event.getPublishedOn().isAfter(LocalDateTime.now()))
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        if (event.getParticipantLimit() <= event.getConfirmedRequests())
            throw new ConflictException("У события достигнут лимит запросов на участие");

        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);

        if (!event.isRequestModeration())
            request.setStatus(Status.CONFIRMED);

        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(long userId, long requestId) {
        return null;
    }

    @Override
    public ParticipationRequestDto getUserEventRequests(long userId, long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateRequest updateUserEventRequests(long userId, long eventId,
                                                                   EventRequestStatusUpdateRequest request) {
        return null;
    }
}
