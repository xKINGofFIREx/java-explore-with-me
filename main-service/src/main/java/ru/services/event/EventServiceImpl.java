package ru.services.event;

import dtos.main.event.EventFullDto;
import dtos.main.event.EventShortDto;
import dtos.main.request.UpdateEventAdminRequest;
import org.springframework.stereotype.Repository;
import ru.models.Sort;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EventServiceImpl implements EventService {
    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categoryIds, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable, Sort sort, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto getEventById(long eventId) {
        return null;
    }

    @Override
    public List<EventFullDto> searchEvents(List<Long> userIds, List<String> states, List<Long> categoryIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto updateEventById(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }
}
