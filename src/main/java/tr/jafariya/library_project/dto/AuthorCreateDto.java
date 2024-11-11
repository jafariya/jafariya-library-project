package tr.jafariya.library_project.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorCreateDto {
    @Size(min = 2, max = 10)
    @NotBlank(message = "Must write the name")
    private String name;
    @NotBlank(message = "Must write the surname")
    private String surname;
}
