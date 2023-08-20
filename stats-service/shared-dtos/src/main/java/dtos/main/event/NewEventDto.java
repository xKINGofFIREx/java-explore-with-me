package dtos.main.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import dtos.main.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotNull
    @NotBlank
    private String annotation;

    private long category;

    @NotNull
    @NotBlank
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;
}
