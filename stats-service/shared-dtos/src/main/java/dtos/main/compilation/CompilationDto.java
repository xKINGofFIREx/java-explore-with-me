package dtos.main.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private long id;
    private boolean pinned;
    private String title;
}