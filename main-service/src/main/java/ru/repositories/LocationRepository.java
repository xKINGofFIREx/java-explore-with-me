package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.models.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
