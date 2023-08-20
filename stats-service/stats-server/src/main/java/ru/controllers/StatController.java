package ru.controllers;

import dtos.EndpointHitDto;
import dtos.ViewStatsDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.services.StatService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@AllArgsConstructor
public class StatController {
    private StatService statService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
                LocalDateTime.parse(start, FORMATTER),
                LocalDateTime.parse(end, FORMATTER),
                uris, unique
        );
    }
}
