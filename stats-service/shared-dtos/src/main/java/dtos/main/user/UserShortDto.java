package dtos.main.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    private long id;

    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}
