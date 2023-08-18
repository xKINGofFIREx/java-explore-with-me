package dtos.main.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRequest {
    private Set<Long> events;
    private boolean pinned;
    private String title;
}
