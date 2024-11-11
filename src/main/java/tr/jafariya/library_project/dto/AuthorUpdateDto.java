package tr.jafariya.library_project.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorUpdateDto {

    private Long id;
    @Size(min = 2, max = 25)
    @NotBlank(message = "Must write the name of the author")
    private String name;
    @Size(min = 2, max = 25)
    @NotBlank(message = "Must write the surname of the author")
    private String surname;
}
