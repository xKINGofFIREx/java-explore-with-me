package dtos.event;

import dtos.Location;
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
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;
}
