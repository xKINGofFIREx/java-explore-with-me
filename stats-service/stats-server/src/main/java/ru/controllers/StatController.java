package ru.controllers;

import dtos.EndpointHitDto;
import dtos.ViewStatsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.services.StatService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<ViewStatsDto> getStats(@RequestParam("start") String start,
                                       @RequestParam("end") String end,
                                       @RequestParam(required = false, name = "uris") List<String> uris,
                                       @RequestParam(required = false, name = "unique") Boolean unique) {

        start = URLDecoder.decode(start, StandardCharsets.UTF_8);
        end = URLDecoder.decode(end, StandardCharsets.UTF_8);

        return statService.getStats(
                LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                uris, unique
        );
    }
}
