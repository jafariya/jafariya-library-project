package tr.jafariya.library_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreateDto {
    @Size (min = 2, max = 50)
    @NotBlank(message = "Must write the name of the book")
    private String name;
    @Size (min = 2, max = 20)
    @NotBlank(message = "Must write the genre of the book")
    private String genreName;
    @NotBlank(message = "Must include the id of the author")
    private Long authorId;
}
