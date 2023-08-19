package ru.services.event;

import dtos.main.State;
import dtos.main.event.EventFullDto;
import dtos.main.event.EventShortDto;
import dtos.main.event.NewEventDto;
import dtos.main.request.UpdateEventAdminRequest;
import dtos.main.request.UpdateEventUserRequest;
import ru.exceptions.ConflictException;
import ru.exceptions.NotFoundException;
import ru.models.Sort;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getEvents(String text, List<Long> categoryIds, Boolean paid,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                  boolean onlyAvailable, Sort sort, int from, int size);

    EventFullDto getEventById(long eventId) throws NotFoundException;

    List<EventFullDto> searchEvents(List<Long> userIds, List<State> states, List<Long> categoryIds,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto updateEventById(long eventId, UpdateEventAdminRequest request) throws NotFoundException, ConflictException;

    List<EventShortDto> getUserEvents(long userId, int from, int size);

    EventFullDto createUserEvent(long userId, NewEventDto newEventDto) throws ConflictException;

    EventFullDto getUserEventById(long userId, long eventId) throws NotFoundException;

    EventFullDto updateUserEventById(long userId, long eventId, UpdateEventUserRequest request) throws NotFoundException, ConflictException;

}
