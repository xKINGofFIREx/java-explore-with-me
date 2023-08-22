package ru.repositories;

import dtos.main.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.models.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e where e.state = :state " +
            "and (:onlyAvailable = false or e.participantLimit > e.confirmedRequests) " +
            "and (:paid = null or e.paid = :paid) " +
            "and (:text = null or lower(e.annotation) like lower(:text) or lower(e.description) like lower(:text)) " +
            "and (:categoryIds = null or e.category.id in :categoryIds) " +
            "and (date(:start) = null or e.eventDate > date(:start)) " +
            "and (date(:end) = null or e.eventDate < date(:end))"
    )
    Page<Event> getPublicEvents(@Param("state") State state, Pageable pageable,
                                @Param("onlyAvailable") boolean onlyAvailable,
                                @Param("paid") Boolean paid,
                                @Param("text") String text,
                                @Param("categoryIds") List<Long> categoryIds,
                                @Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end
    );

    @Query("select e from Event e where (:userIds = null or e.initiator.id in :userIds) " +
            "and (:states = null or e.state in :states) " +
            "and (:categoryIds = null or e.category.id in :categoryIds) " +
            "and (date(:start) = null or e.eventDate > date(:start)) " +
            "and (date(:end) = null or e.eventDate < date(:end))"
    )
    Page<Event> getAdminEvents(Pageable pageable,
                               @Param("userIds") List<Long> userIds,
                               @Param("states") List<State> states,
                               @Param("categoryIds") List<Long> categoryIds,
                               @Param("start") LocalDateTime start,
                               @Param("end") LocalDateTime end
    );

    Page<Event> getEventsByInitiatorId(long userId, Pageable pageable);

    Event getEventByIdAndInitiatorId(long eventId, long userId);
}
