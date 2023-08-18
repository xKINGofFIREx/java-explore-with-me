package dtos.main.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private long id;
    private LocalDateTime created;
    private long eventId;
    private long requesterId;
    private String status;
}
