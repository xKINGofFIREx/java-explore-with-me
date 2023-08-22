package dtos.main.compilation;

import dtos.main.event.EventShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private long id;
    private boolean pinned;
    private String title;
    private List<EventShortDto> events;
}
