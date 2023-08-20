package ru.repositories;

import dtos.main.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.models.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e where e.state = :state")
    List<Event> getPublicEvents(@Param("state") State state);

    Page<Event> getEventsByInitiatorId(long userId, Pageable pageable);

    Event getEventByIdAndInitiatorId(long eventId, long userId);
}
