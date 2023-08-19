package ru.mappers;

import dtos.main.State;
import dtos.main.event.EventFullDto;
import dtos.main.event.EventShortDto;
import dtos.main.event.NewEventDto;
import ru.models.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventMapper {

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.isPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static List<EventShortDto> toEventShortDtos(List<Event> events) {
        List<EventShortDto> eventShortDtos = new ArrayList<>();
        for (Event event : events)
            eventShortDtos.add(EventMapper.toEventShortDto(event));
        return eventShortDtos;
    }

    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator()),
                LocationMapper.toLocationDto(event.getLocation()),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.isRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static List<EventFullDto> toEventFullDtos(List<Event> events) {
        List<EventFullDto> eventFullDtos = new ArrayList<>();
        for (Event event : events)
            eventFullDtos.add(EventMapper.toEventFullDto(event));
        return eventFullDtos;
    }

    public static Event toEventFromNewEventDto(NewEventDto newEventDto) {
        return new Event(
                0,
                null,
                LocationMapper.toLocation(newEventDto.getLocation()),
                null,
                0,
                0,
                newEventDto.getAnnotation(),
                newEventDto.getDescription(),
                State.PENDING,
                newEventDto.getTitle(),
                null,
                newEventDto.getEventDate(),
                LocalDateTime.now(),
                newEventDto.isRequestModeration(),
                newEventDto.isPaid(),
                newEventDto.getParticipantLimit()
        );
    }
}
