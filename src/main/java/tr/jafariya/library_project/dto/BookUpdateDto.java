package tr.jafariya.library_project.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class BookUpdateDto {

    @Size(min = 2, max = 25)
    @NotBlank(message = "Must write the name of the book")
    String name;
    @Size(min = 2, max = 25)
    @NotBlank(message = "Must write the  genre name of the book")
    String genreName;
    private List<Long> authorIds;
    Long id;
}
