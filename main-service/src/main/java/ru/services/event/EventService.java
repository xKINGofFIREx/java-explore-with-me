package ru.services.event;

import dtos.main.event.EventFullDto;
import dtos.main.event.EventShortDto;
import dtos.main.request.UpdateEventAdminRequest;
import ru.models.Sort;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getEvents(String text, List<Long> categoryIds, Boolean paid,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                  Boolean onlyAvailable, Sort sort, int from, int size);

    EventFullDto getEventById(long eventId);

    List<EventFullDto> searchEvents(List<Long> userIds, List<String> states, List<Long> categoryIds,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto updateEventById(long eventId, UpdateEventAdminRequest updateEventAdminRequest);
}
