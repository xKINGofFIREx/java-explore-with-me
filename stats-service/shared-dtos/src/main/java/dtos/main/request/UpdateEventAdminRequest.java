package dtos.main.request;

import dtos.main.LocationDto;
import dtos.main.StateAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventAdminRequest {
    private String annotation;
    private long categoryId;
    private String description;
    private LocalDateTime eventDate;
    private LocationDto locationDto;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private StateAction stateAction;
    private String title;
}