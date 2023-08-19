package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
