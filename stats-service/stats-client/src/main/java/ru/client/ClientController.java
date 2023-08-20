package ru.client;

import dtos.stats.EndpointHitDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClientController {
    private BaseClient baseClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> saveHit(@RequestBody EndpointHitDto hitDto) {
        return new ResponseEntity<>(baseClient.saveHit(hitDto), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam("start") String start,
                                           @RequestParam("end") String end,
                                           @RequestParam(required = false, name = "uris") List<String> uris,
                                           @RequestParam(required = false, name = "unique") Boolean unique) {
        return baseClient.getStats(start, end, uris, unique);
    }
}
