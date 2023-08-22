package ru.services;

import dtos.stats.EndpointHitDto;
import dtos.stats.ViewStatsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.mappers.EndpointHitMapper;
import ru.models.EndpointHit;
import ru.repositories.EndpointHitRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class StatServiceImpl implements StatService {
    private EndpointHitRepository hitRepository;

    public EndpointHitDto saveHit(EndpointHitDto hitDto) {
        EndpointHit endpointHit = hitRepository.save(EndpointHitMapper.toEndpointHit(hitDto));
        return EndpointHitMapper.toEndpointHitDto(endpointHit);
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start.isAfter(end))
            throw new ValidationException();
        if (uris != null && unique != null && unique)
            return hitRepository.findAllByHitDateUnique(start, end, uris);

        if (uris != null)
            return hitRepository.findAllByHitDate(start, end, uris);

        return hitRepository.findAllByHitDate(start, end);
    }
}
