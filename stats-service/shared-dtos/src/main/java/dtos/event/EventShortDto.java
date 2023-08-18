package dtos.event;

import dtos.category.CategoryDto;
import dtos.user.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private long id;
    private String annotation;
    private CategoryDto categoryDto;
    private long confirmedRequests;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private long views;
}
