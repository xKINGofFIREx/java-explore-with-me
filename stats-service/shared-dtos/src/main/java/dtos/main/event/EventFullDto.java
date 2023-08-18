package dtos.main.event;

import dtos.main.Location;
import dtos.main.State;
import dtos.main.category.CategoryDto;
import dtos.main.user.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private long id;
    private String annotation;
    private CategoryDto categoryDto;
    private long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Location location;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private long views;
}
