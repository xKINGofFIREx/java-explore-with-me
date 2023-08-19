package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.models.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
    boolean existsByRequesterIdAndEventId(long userId, long eventId);
}
