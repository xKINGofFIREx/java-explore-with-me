package ru.repositories;

import dtos.ViewStatsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.models.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {

    @Query("select new dtos.ViewStatsDto(h.app, h.uri, count(h.uri)) " +
            "from EndpointHit h " +
            "where h.timestamp between :start and :end " +
            "group by h.app, h.uri " +
            "order by count(h.uri) " +
            "desc")
    List<ViewStatsDto> findAllByHitDate(@Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

    @Query("select new dtos.ViewStatsDto(h.app, h.uri, count(h.uri)) " +
            "from EndpointHit h " +
            "where h.uri in :uris and h.timestamp between :start and :end " +
            "group by h.app, h.uri " +
            "order by count(h.uri) " +
            "desc")
    List<ViewStatsDto> findAllByHitDate(@Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end,
                                        @Param("uris") List<String> uris);

    @Query("select new dtos.ViewStatsDto(h.app, h.uri, 1L) " +
            "from EndpointHit h " +
            "where h.uri in :uris and h.timestamp between :start and :end " +
            "group by h.ip, h.app, h.uri")
    List<ViewStatsDto> findAllByHitDateUnique(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end,
                                              @Param("uris") List<String> uris);
}
