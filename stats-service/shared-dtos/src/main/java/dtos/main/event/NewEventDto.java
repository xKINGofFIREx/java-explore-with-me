package dtos.main.event;

import dtos.main.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    private String annotation;
    private long categoryId;
    private String description;
    private LocalDateTime eventDate;
    private LocationDto locationDto;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;
}
