package ru.services.request;

import dtos.main.Status;
import dtos.main.request.EventRequestStatusUpdateRequest;
import dtos.main.request.EventRequestStatusUpdateResult;
import dtos.main.request.ParticipationRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.exceptions.ConflictException;
import ru.exceptions.NotFoundException;
import ru.mappers.RequestMapper;
import ru.models.Event;
import ru.models.Request;
import ru.models.User;
import ru.repositories.EventRepository;
import ru.repositories.RequestRepository;
import ru.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getUserRequest(long userId) throws NotFoundException {
        if (!userRepository.existsById(userId))
            throw new NotFoundException("Пользователь не найден");

        return RequestMapper.toParticipationRequestDtos(requestRepository.getRequestsByRequesterId(userId));
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
        if (event.getParticipantLimit() < event.getConfirmedRequests())
            throw new ConflictException("У события достигнут лимит запросов на участие");

        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);

        if (event.isRequestModeration())
            request.setStatus(Status.CONFIRMED);

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(long userId, long requestId) throws NotFoundException, ConflictException {
        if (!requestRepository.existsById(requestId))
            throw new NotFoundException("Запрос не найден или недоступен");

        Request request = requestRepository.getReferenceById(requestId);
        if (request.getRequester().getId() != userId)
            throw new ConflictException("");

        request.setStatus(Status.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getUserEventRequests(long userId, long eventId) {
        List<Request> requests = requestRepository
                .getRequestsByRequesterIdAndEventId(userId, eventId);
        return RequestMapper.toParticipationRequestDtos(requests);
    }

    @Override
    public EventRequestStatusUpdateResult updateUserEventRequest(long userId, long eventId,
                                                                 EventRequestStatusUpdateRequest updateRequest) throws NotFoundException, ConflictException {
        if (!eventRepository.existsById(eventId))
            throw new NotFoundException("Событие не найдено или недоступно");

        Event event = eventRepository.getReferenceById(eventId);
        if (event.getParticipantLimit() < event.getConfirmedRequests())
            throw new ConflictException("У события достигнут лимит запросов на участие");

        EventRequestStatusUpdateResult updateResult = new EventRequestStatusUpdateResult();
        List<Request> requests = requestRepository.findAllByRequestsIds(updateRequest.getRequestIds());

        if (event.getParticipantLimit() == 0 || !event.isRequestModeration()) {
            for (Request request : requests)
                request.setStatus(updateRequest.getStatus());

            requestRepository.saveAll(requests);
            if (updateRequest.getStatus() == Status.CONFIRMED)
                updateResult.setConfirmedRequests(RequestMapper.toParticipationRequestDtos(requests));
            else
                updateResult.setRejectedRequests(RequestMapper.toParticipationRequestDtos(requests));
            return updateResult;
        }

        for (Request request : requests) {
            if (event.getParticipantLimit() >= event.getConfirmedRequests()) {
                request.setStatus(updateRequest.getStatus());
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                updateResult.getConfirmedRequests().add(RequestMapper.toParticipationRequestDto(request));
            } else {
                request.setStatus(Status.REJECTED);
                updateResult.getRejectedRequests().add(RequestMapper.toParticipationRequestDto(request));
            }
        }

        requestRepository.saveAll(requests);
        return updateResult;
    }
}
