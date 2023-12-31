package ru.repositories;

import dtos.main.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.models.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    boolean existsByRequesterIdAndEventId(long userId, long eventId);

    List<Request> getRequestsByRequesterId(long userId);

    @Query("select r from Request r where r.event.initiator.id = :userId and r.event.id = :eventId")
    List<Request> getRequestsByRequesterIdAndEventId(@Param("userId") long userId, @Param("eventId") long eventId);

    @Query("select r from Request r where r.id in :ids")
    List<Request> findAllByRequestsIds(@Param("ids") List<Long> ids);

    @Query("select count(r) from Request r where r.status = :status and r.id in :ids")
    long getRequestsCountByStatusAndIds(@Param("status") Status status, List<Long> ids);
}
