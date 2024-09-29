package tr.jafariya.library_project.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.jafariya.library_project.model.Book;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorDto {
    private Long id;
    private String name;
    private String surname;
    private List<BookDto> books;

}