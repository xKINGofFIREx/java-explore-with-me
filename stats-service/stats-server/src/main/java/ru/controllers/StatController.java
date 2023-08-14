package ru.controllers;

import dtos.EndpointHitDto;
import dtos.ViewStatsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.services.StatService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class StatController {
    private StatService statService;

    @PostMapping("/hit")
    public EndpointHitDto saveHit(@RequestBody EndpointHitDto hitDto) {
        return statService.saveHit(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                       @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                       @RequestParam(required = false, name = "uris") List<String> uris,
                                       @RequestParam(required = false, name = "unique") Boolean unique) {
        return statService.getStats(start, end, uris, unique);
    }
}
