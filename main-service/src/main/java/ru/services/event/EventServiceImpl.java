package ru.services.event;

import dtos.main.State;
import dtos.main.StateAction;
import dtos.main.event.EventFullDto;
import dtos.main.event.EventShortDto;
import dtos.main.event.NewEventDto;
import dtos.main.request.UpdateEventAdminRequest;
import dtos.main.request.UpdateEventUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.exceptions.ConflictException;
import ru.exceptions.NotFoundException;
import ru.mappers.EventMapper;
import ru.models.Event;
import ru.models.Location;
import ru.models.Sort;
import ru.repositories.CategoryRepository;
import ru.repositories.EventRepository;
import ru.repositories.LocationRepository;
import ru.repositories.UserRepository;

import javax.validation.ValidationException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categoryIds, Boolean paid,
                                         String rangeStart, String rangeEnd,
                                         boolean onlyAvailable, Sort sort, int from, int size) {

        LocalDateTime start = decodeString(rangeStart);
        LocalDateTime end = decodeString(rangeEnd);


        if (start != null && end != null && start.isAfter(end))
            throw new ValidationException();

        Stream<Event> stream = eventRepository.getPublicEvents(State.PUBLISHED).stream()
                .filter(e -> !onlyAvailable || e.getParticipantLimit() > e.getConfirmedRequests())
                .filter(e -> paid == null || paid == e.isPaid())
                .filter(e -> text == null || (e.getAnnotation().toLowerCase().contains(text.toLowerCase())
                        || e.getDescription().toLowerCase().contains(text.toLowerCase())))
                .filter(e -> categoryIds == null || categoryIds.contains(e.getCategory().getId()))
                .filter(e -> start == null || e.getEventDate().isAfter(start))
                .filter(e -> end == null || e.getEventDate().isBefore(end))
                .skip(from)
                .limit(size);

        if (sort == Sort.EVENT_DATE)
            stream = stream.sorted(Comparator.comparing(Event::getEventDate));
        if (sort == Sort.VIEWS)
            stream = stream.sorted(Comparator.comparing(Event::getViews));

        return EventMapper.toEventShortDtos(stream.collect(Collectors.toList()));
    }

    @Override
    public EventFullDto getEventById(long eventId) throws NotFoundException {
        if (!eventRepository.existsById(eventId)
                || eventRepository.getReferenceById(eventId).getState() != State.PUBLISHED)
            throw new NotFoundException("Событие не найдено или недоступно");
        Event event = eventRepository.getReferenceById(eventId);
        event.setViews(event.getViews() + 1);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventFullDto> searchEvents(List<Long> userIds, List<State> states, List<Long> categoryIds,
                                           String rangeStart, String rangeEnd, int from, int size) {
        LocalDateTime start = decodeString(rangeStart);
        LocalDateTime end = decodeString(rangeEnd);

        if (start != null && end != null && start.isAfter(end))
            throw new ValidationException();

        return EventMapper.toEventFullDtos(eventRepository.findAll().stream()
                .filter(e -> userIds == null || userIds.contains(e.getInitiator().getId()))
                .filter(e -> states == null || states.contains(e.getState()))
                .filter(e -> categoryIds == null || categoryIds.contains(e.getCategory().getId()))
                .filter(e -> start == null || e.getEventDate().isAfter(start))
                .filter(e -> end == null || e.getEventDate().isBefore(end))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList()));

    }

    @Override
    public EventFullDto updateEventById(long eventId,
                                        UpdateEventAdminRequest request) throws NotFoundException, ConflictException {

        if (!eventRepository.existsById(eventId))
            throw new NotFoundException("Событие не найдено или недоступно");

        Event event = eventRepository.getReferenceById(eventId);

        if (event.getPublishedOn().plusHours(1).isAfter(event.getEventDate()))
            throw new ConflictException("Дата начала изменяемого события должна быть не ранее чем за час от даты публикации.");
        if (event.getState() != State.PENDING && request.getStateAction() == StateAction.PUBLISH_EVENT)
            throw new ConflictException("Событие можно публиковать, только если оно в состоянии ожидания публикации");
        if (event.getState() == State.PUBLISHED && request.getStateAction() == StateAction.REJECT_EVENT)
            throw new ConflictException("Cобытие можно отклонить, только если оно еще не опубликовано");

        if (request.getAnnotation() != null)
            event.setAnnotation(request.getAnnotation());
        if (request.getCategory() != 0)
            event.setCategory(categoryRepository.getReferenceById(request.getCategory()));
        if (request.getDescription() != null)
            event.setDescription(request.getDescription());
        if (request.getEventDate() != null)
            event.setEventDate(request.getEventDate());
        if (request.getLocation() != null) {
            Location location = locationRepository.getReferenceById(event.getLocation().getId());
            location.setLat(request.getLocation().getLat());
            location.setLon(request.getLocation().getLon());
            locationRepository.save(location);
            event.setLocation(location);

        }
        if (request.getPaid() != null)
            event.setPaid(request.getPaid());
        if (request.getParticipantLimit() != 0 && request.getParticipantLimit() != event.getParticipantLimit())
            event.setParticipantLimit(request.getParticipantLimit());
        if (request.getTitle() != null)
            event.setTitle(request.getTitle());
        event.setState(request.getStateAction() == StateAction.PUBLISH_EVENT ? State.PUBLISHED : State.CANCELED);


        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> getUserEvents(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        return EventMapper.toEventShortDtos(eventRepository.getEventsByInitiatorId(userId, pageable).toList());
    }

    @Override
    public EventFullDto createUserEvent(long userId, NewEventDto newEventDto) throws ConflictException {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2)))
            throw new ConflictException("Событие не удовлетворяет правилам создания");

        Event event = EventMapper.toEventFromNewEventDto(newEventDto);
        event.setCategory(categoryRepository.getReferenceById(newEventDto.getCategory()));
        event.setInitiator(userRepository.getReferenceById(userId));

        locationRepository.save(event.getLocation());

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto getUserEventById(long userId, long eventId) throws NotFoundException {
        if (!eventRepository.existsById(eventId))
            throw new NotFoundException("Событие не найдено или недоступно");

        return EventMapper.toEventFullDto(eventRepository.getEventByIdAndInitiatorId(eventId, userId));
    }

    @Override
    public EventFullDto updateUserEventById(long userId, long eventId,
                                            UpdateEventUserRequest request) throws NotFoundException, ConflictException {
        if (!eventRepository.existsById(eventId))
            throw new NotFoundException("Событие не найдено или недоступно");
        if (request.getEventDate() != null && LocalDateTime.now().plusHours(2).isAfter(request.getEventDate()))
            throw new ConflictException("Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента");

        Event event = eventRepository.getReferenceById(eventId);

        if (event.getState() == State.PUBLISHED || !request.isRequestModeration())
            throw new ConflictException("Изменить можно только отмененные события или события в состоянии ожидания модерации");

        if (request.getAnnotation() != null)
            event.setAnnotation(request.getAnnotation());
        if (request.getCategory() != 0)
            event.setCategory(categoryRepository.getReferenceById(request.getCategory()));
        if (request.getDescription() != null)
            event.setDescription(request.getDescription());
        if (request.getEventDate() != null)
            event.setEventDate(request.getEventDate());
        if (request.getLocation() != null) {
            Location location = locationRepository.getReferenceById(event.getLocation().getId());
            location.setLat(request.getLocation().getLat());
            location.setLon(request.getLocation().getLon());
            locationRepository.save(location);
            event.setLocation(location);
        }
        if (request.getTitle() != null)
            event.setTitle(request.getTitle());
        event.setState(request.getStateAction() == StateAction.PUBLISH_EVENT ? State.PUBLISHED : State.CANCELED);
        if (request.getStateAction() == StateAction.SEND_TO_REVIEW)
            event.setState(State.PENDING);

        return EventMapper.toEventFullDto(event);
    }

    private LocalDateTime decodeString(String date) {
        if (date != null) {
            date = URLDecoder.decode(date, StandardCharsets.UTF_8);
            return LocalDateTime.parse(date, FORMATTER);
        } else {
            return null;
        }
    }
}
